package org.emall.area.manager;

import org.emall.area.cache.AreaMemoryCache;
import org.emall.area.model.domain.AreaCode;
import org.emall.area.model.dto.AreaDto;
import org.emall.area.model.dto.AreaForViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/2/23
 */
@Component
public class AreaManager {

    @Autowired
    private AreaMemoryCache areaMemoryCache;

    public List<AreaDto> parseCode(String code) {
        List<String> codes = AreaCode.parse(code);

        return areaMemoryCache.getByCode(codes);
    }

    public String getName(String code) {
        return getName(parseCode(code));
    }

    private String getName(List<AreaDto> areaDtoList) {
        return areaDtoList.stream()
                .map(AreaDto::getName)
                .collect(Collectors.joining());
    }

    public List<AreaDto> getByParentId(Integer parentId) {
        return areaMemoryCache.getByParentId(parentId)
                .orElse(Collections.emptyList());
    }

    public String getNameNoProvince(String code) {
        if (AreaCode.isSelfCity(code)) {
            return getName(code);
        }

        return parseCode(code).stream()
                .skip(1)
                .map(AreaDto::getName)
                .collect(Collectors.joining());
    }

    public AreaForViewDto parseCodeForView(String code) {
        List<AreaDto> areaDtoList = parseCode(code);

        return new AreaForViewDto()
                .setCode(code)
                .setName(getName(areaDtoList))
                .setLevels(areaDtoList)
                .setPerLevelAll(areaDtoList.stream()
                        .map(a -> getByParentId(a.getParentId()))
                        .collect(Collectors.toList()));
    }
}
