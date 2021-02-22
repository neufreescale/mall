package org.emall.area.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.diwayou.core.exception.CustomException;
import org.emall.area.model.domain.AreaCode;
import org.emall.area.model.dto.AreaDto;
import org.emall.area.model.entity.Area;
import org.emall.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/2/23
 */
@Component
@Slf4j
public class AreaMemoryCache {

    private static final int PARENT_CACHE_SIZE = 1000;

    private static final int CODE_CACHE_SIZE = 10000;

    @Autowired
    private AreaService areaService;

    private LoadingCache<Integer, List<AreaDto>> parentToArea;

    private LoadingCache<String, AreaDto> codeToArea;

    @PostConstruct
    public void init() {
        parentToArea = CacheBuilder.newBuilder()
                .maximumSize(PARENT_CACHE_SIZE)
                .build(new CacheLoader<Integer, List<AreaDto>>() {
                    @Override
                    public List<AreaDto> load(Integer key) throws Exception {
                        List<Area> areas = areaService.getByParentId(key);
                        if (CollectionUtils.isEmpty(areas)) {
                            return Collections.emptyList();
                        }

                        return areas.stream()
                                .map(a -> a.to(AreaDto.class))
                                .collect(Collectors.toList());
                    }
                });

        codeToArea = CacheBuilder.newBuilder()
                .maximumSize(CODE_CACHE_SIZE)
                .build(new CacheLoader<String, AreaDto>() {
                    @Override
                    public AreaDto load(String key) throws Exception {
                        Area area = areaService.getByCode(key);
                        if (area == null) {
                            throw new CustomException("code不存在!");
                        }

                        return area.to(AreaDto.class);
                    }
                });
    }

    public Optional<List<AreaDto>> getByParentId(Integer parentId) {
        if (parentId == null) {
            throw new CustomException("parentId不能为空!");
        }

        try {
            return Optional.of(parentToArea.get(parentId));
        } catch (Exception e) {
            log.debug("fail parentId={}", parentId, e);
        }

        return Optional.empty();
    }

    public Optional<AreaDto> getByCode(String code) {
        if (!AreaCode.isValid(code)) {
            throw new CustomException("code格式不正确" + code);
        }

        try {
            return Optional.of(codeToArea.get(code));
        } catch (Exception e) {
            log.debug("fail code={}", code, e);
        }

        return Optional.empty();
    }

    public List<AreaDto> getByCode(Collection<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return Collections.emptyList();
        }

        List<AreaDto> result = new ArrayList<>(codes.size());
        for (String code : codes) {
            Optional<AreaDto> opArea = getByCode(code);
            if (!opArea.isPresent()) {
                continue;
            }

            result.add(opArea.get());
        }

        return result;
    }
}
