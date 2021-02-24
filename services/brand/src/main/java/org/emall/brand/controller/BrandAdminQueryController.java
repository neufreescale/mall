package org.emall.brand.controller;

import org.emall.brand.manager.BrandQueryManager;
import org.emall.brand.model.domain.BrandSimple;
import org.emall.brand.model.request.BrandListRequest;
import org.emall.brand.model.request.BrandSearchRequest;
import org.emall.brand.model.response.BrandGetResponse;
import org.emall.brand.model.response.BrandListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gaopeng 2021/2/24
 */
@RestController
@RequestMapping("/admin/brand")
public class BrandAdminQueryController {

    @Autowired
    private BrandQueryManager brandQueryManager;

    @GetMapping(path = "/get", name = "按照id查询品牌信息")
    public BrandGetResponse get(@RequestParam(name = "id") Integer id) {
        return brandQueryManager.get(id);
    }

    @GetMapping(path = "/list", name = "查询品牌列表")
    public List<BrandListResponse> list(@Validated BrandListRequest request) {
        return brandQueryManager.list(request);
    }

    @GetMapping(path = "/list/count", name = "查询品牌列表count")
    public Integer listCount(@Validated BrandListRequest request) {
        return brandQueryManager.listCount(request);
    }

    @GetMapping(path = "/search", name = "按照名称查询品牌列表")
    public List<BrandSimple> search(@Validated BrandSearchRequest request) {
        return brandQueryManager.search(request);
    }

    @GetMapping(path = "/search/count", name = "按照名称查询品牌列表count")
    public Integer searchCount(@Validated BrandSearchRequest request) {
        return brandQueryManager.searchCount(request);
    }
}
