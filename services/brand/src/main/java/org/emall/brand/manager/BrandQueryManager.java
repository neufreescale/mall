package org.emall.brand.manager;

import org.diwayou.core.exception.CustomException;
import org.emall.brand.model.domain.BrandListQuery;
import org.emall.brand.model.domain.BrandSimple;
import org.emall.brand.model.entity.Brand;
import org.emall.brand.model.request.BrandListRequest;
import org.emall.brand.model.request.BrandSearchRequest;
import org.emall.brand.model.response.BrandGetResponse;
import org.emall.brand.model.response.BrandListResponse;
import org.emall.brand.service.BrandQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/2/24
 */
@Component
public class BrandQueryManager {

    @Autowired
    private BrandQueryService brandQueryService;

    public BrandGetResponse get(Integer id) {
        Brand brand = brandQueryService.masterGetById(id);
        if (brand == null) {
            throw new CustomException("品牌信息不存在");
        }

        return brand.to(BrandGetResponse.class);
    }

    public List<BrandListResponse> list(BrandListRequest request) {
        BrandListQuery query = request.toP(BrandListQuery.class);

        List<Brand> brands = brandQueryService.list(query);

        return brands.stream()
                .map(b -> b.to(BrandListResponse.class))
                .collect(Collectors.toList());
    }

    public Integer listCount(BrandListRequest request) {
        BrandListQuery query = request.toP(BrandListQuery.class);

        return brandQueryService.listCount(query);
    }

    public List<BrandSimple> search(BrandSearchRequest request) {
        BrandListQuery query = request.toP(BrandListQuery.class);

        List<Brand> brands = brandQueryService.list(query);

        return brands.stream()
                .map(b -> b.to(BrandSimple.class))
                .collect(Collectors.toList());
    }

    public Integer searchCount(BrandSearchRequest request) {
        BrandListQuery query = request.toP(BrandListQuery.class);

        return brandQueryService.listCount(query);
    }
}
