package org.emall.search.manager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.emall.search.model.domain.Score;
import org.emall.search.model.response.ScorePageResponse;
import org.emall.search.model.response.ScoreStatResponse;
import org.emall.user.client.api.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author gaopeng 2021/9/7
 */
@Component
@Slf4j
public class ScoreSearchManager {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @DubboReference(check = false)
    private UserApi userApi;

    @PostConstruct
    public void init() {
        try {
            IndexOperations indexOperations = restTemplate.indexOps(Score.class);
            if (!indexOperations.exists()) {
                indexOperations.createWithMapping();
                log.info("创建es索引score成功");
            }
        } catch (Exception e) {
            log.warn("创建es索引失败", e);
        }
    }

    public List<Score> index() {
        List<Score> scores = userApi.rank(2021).stream()
                .map(s -> s.to(Score.class))
                .collect(Collectors.toList());

        restTemplate.save(scores);

        return scores;
    }

    public ScorePageResponse search(String name) {
        QueryBuilder queryBuilder = boolQuery().should(multiMatchQuery(name, "scName", "spName")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .should(termQuery("scName.raw", name).boost(5));

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<Score> hits = restTemplate.search(nativeSearchQuery, Score.class);

        List<Score> scores = hits.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return new ScorePageResponse()
                .setTotal(hits.getTotalHits())
                .setScores(scores);
    }

    public List<ScoreStatResponse> stat(int minSort, int maxSort, int interval) {
        QueryBuilder queryBuilder = rangeQuery("sort")
                .lte(maxSort)
                .gte(minSort);
        AbstractAggregationBuilder<?> aggregationBuilder = AggregationBuilders
                .histogram("histogram")
                .field("sort")
                .interval(interval)
                .subAggregation(AggregationBuilders
                        .terms("school")
                        .field("scName.raw")
                        .size(1000));

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withMaxResults(0)
                .addAggregation(aggregationBuilder)
                .build();

        SearchHits<Score> hits = restTemplate.search(nativeSearchQuery, Score.class);

        ParsedHistogram parsedHistogram = Objects.requireNonNull(hits.getAggregations()).get("histogram");

        List<ScoreStatResponse> result = Lists.newArrayListWithCapacity(parsedHistogram.getBuckets().size());
        for (Histogram.Bucket bucket : parsedHistogram.getBuckets()) {
            ScoreStatResponse response = new ScoreStatResponse(bucket.getKeyAsString());

            ParsedStringTerms parsedStringTerms = bucket.getAggregations().get("school");
            List<ScoreStatResponse.Stat> statList = Lists.newArrayListWithCapacity(parsedStringTerms.getBuckets().size());
            for (Terms.Bucket termBucket : parsedStringTerms.getBuckets()) {
                statList.add(new ScoreStatResponse.Stat(termBucket.getKeyAsString(), termBucket.getDocCount()));
            }

            response.setStatList(statList);
            result.add(response);
        }

        return result;
    }
}
