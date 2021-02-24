package org.emall.brand.controller;

import org.emall.brand.manager.BrandManager;
import org.emall.brand.model.request.BrandCreateRequest;
import org.emall.brand.model.request.BrandUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaopeng 2021/2/24
 */
@RestController
@RequestMapping("/admin/brand")
public class BrandAdminController {

    @Autowired
    private BrandManager brandManager;

    @PostMapping(path = "/create", name = "创建品牌")
    public Integer create(@Validated @RequestBody BrandCreateRequest request) {
        return brandManager.createWithLock(request);
    }

    @PostMapping(path = "/update", name = "更新品牌")
    public void update(@Validated @RequestBody BrandUpdateRequest request) {
        brandManager.updateWithLock(request);
    }
}
