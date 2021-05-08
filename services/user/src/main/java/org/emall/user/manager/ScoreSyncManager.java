package org.emall.user.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.diwayou.core.exception.CustomException;
import org.diwayou.core.json.Json;
import org.emall.user.model.domain.ScoreRecord;
import org.emall.user.model.domain.ScoreResult;
import org.emall.user.model.domain.ScoreWrapper;
import org.emall.user.model.entity.School;
import org.emall.user.model.entity.Score;
import org.emall.user.util.ScoreDataUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/5/6
 */
@Component
@Slf4j
public class ScoreSyncManager {

    private static final String MAC_NAME = "HmacSHA1";

    private static final String prefix = "https://";

    private static final String urlTemplate = "api.eol.cn/gkcx/api/?access_token=&local_batch_id={batchId}&local_province_id=21&local_type_id=1&page={page}&school_id={schoolId}&signsafe={signsafe}&size={size}&special_group=&uri=apidata/api/gk/score/special&year={year}";

    // api.eol.cn/gkcx/api/?local_batch_id=14&local_province_id=21&local_type_id=1&page=2&school_id=225&size=10&special_group=&uri=apidata/api/gk/score/special&year=2020
    private static final String urlSafeTemplate = "api.eol.cn/gkcx/api/?local_batch_id=%d&local_province_id=21&local_type_id=1&page=%d&school_id=%d&size=%d&special_group=&uri=apidata/api/gk/score/special&year=%d";

    // https://api.eol.cn/gkcx/api/?access_token=&local_batch_id=14&local_province_id=21&local_type_id=1&page=2&school_id=225&signsafe=61981026db85ba6c5514942d84667159&size=10&special_group=&uri=apidata/api/gk/score/special&year=2020
    private static final String scoreUrl = prefix + urlTemplate;

    private static final Map<Integer, Integer> batchIdMap = Maps.newHashMap();

    static {
        batchIdMap.put(2020, 14);
        batchIdMap.put(2019, 14);
        batchIdMap.put(2018, 14);
        batchIdMap.put(2017, 7);
        batchIdMap.put(2016, 7);
    }

    private WebClient client;

    @PostConstruct
    public void init() {
        client = WebClient.builder()
                .build();
    }

    public List<Score> sync(School school, int year) {
        final int size = 30;
        Integer schoolId = school.getId();
        Integer batchId = batchIdMap.get(year);
        if (batchId == null) {
            throw new CustomException("请配置batchId年份" + year);
        }

        List<Score> result = Lists.newArrayList();
        for (int page = 1; page < 10; page++) {
            List<Score> scores = getScores(year, page, size, schoolId, batchId);

            if (CollectionUtils.isEmpty(scores)) {
                break;
            }

            result.addAll(scores);

            if (CollectionUtils.size(scores) < size) {
                break;
            }
        }

        return result;
    }

    private List<Score> getScores(int year, Integer page, Integer size, Integer schoolId, Integer batchId) {
        String safeCode = encryptSafeCode(String.format(urlSafeTemplate, batchId, page, schoolId, size, year));

        String re = client.get()
                .uri(scoreUrl, batchId, page, schoolId, safeCode, size, year)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ScoreWrapper scoreWrapper = Json.fromJson(re, ScoreWrapper.class);

        String json = ScoreDataUtil.decrypt(scoreWrapper.getData().getText());

        try {
            ScoreResult data = Json.fromJson(json, ScoreResult.class);

            List<ScoreRecord> scoreRecords = data.getItem();

            return scoreRecords.stream()
                    .map(ScoreSyncManager::convert)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("解析失败 {}", json);

            return Collections.emptyList();
        }
    }

    private static Score convert(ScoreRecord record) {
        Score score = record.to(Score.class);

        if (StringUtils.isNumeric(record.getMin())) {
            score.setMin(Integer.parseInt(record.getMin()));
        } else if (StringUtils.isNumeric(record.getAverage())) {
            score.setMin(Integer.parseInt(record.getAverage()));
        } else {
            score.setMin(0);
        }

        if (StringUtils.isNumeric(record.getRank())) {
            score.setRank(Integer.parseInt(record.getRank()));
        } else {
            score.setRank(10000000 - score.getMin());
        }

        return score;
    }

    private static String encryptSafeCode(String url) {
        try {
            byte[] data = hmacSHA1Encrypt(url, "D23ABC@#56");
            String base64 = Base64.getEncoder().encodeToString(data);

            return Hashing.md5().hashString(base64, StandardCharsets.UTF_8).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] hmacSHA1Encrypt(String encryptText, String encryptKey) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = encryptKey.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(StandardCharsets.UTF_8);
        return mac.doFinal(text);
    }
}
