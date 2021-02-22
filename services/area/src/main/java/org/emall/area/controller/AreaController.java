package org.emall.area.controller;

import org.emall.area.cache.AreaMemoryCache;
import org.emall.area.model.dto.AreaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author gaopeng 2021/2/23
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaMemoryCache areaMemoryCache;

    @GetMapping(value = "/children", name = "获取子区域")
    public List<AreaDto> children(@RequestParam(name = "parentId", defaultValue = "0", required = false) Integer parentId) {
        return areaMemoryCache.getByParentId(parentId)
                .orElse(Collections.emptyList());
    }
}
