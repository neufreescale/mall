package org.emall.brand.manager;

import org.apache.commons.lang3.StringUtils;
import org.diwayou.cache.manager.LockManager;
import org.diwayou.core.exception.CustomException;
import org.emall.brand.model.entity.Brand;
import org.emall.brand.model.request.BrandCreateRequest;
import org.emall.brand.model.request.BrandUpdateRequest;
import org.emall.brand.service.BrandQueryService;
import org.emall.brand.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/2/24
 */
@Component
public class BrandManager {

    private static final String KEY = "brand:c";

    private static final int DEFAULT_LOCK_TTL = 10;

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandQueryService brandQueryService;

    @Autowired
    private LockManager lockManager;

    public Integer createWithLock(BrandCreateRequest request) {
        return lockManager.runWithLock(KEY, DEFAULT_LOCK_TTL, () -> create(request));
    }

    private Integer create(BrandCreateRequest request) {
        Brand brand = request.to(Brand.class);

        if (brandQueryService.masterCountByName(request.getName()) > 0) {
            throw new CustomException("品牌名称已存在");
        }

        if (StringUtils.isNotBlank(request.getCertificateCode()) &&
                brandQueryService.masterCountByCode(request.getCertificateCode()) > 0) {
            throw new CustomException("商标注册证编号已存在");
        }

        brandService.create(brand);

        return brand.getId();
    }

    public void updateWithLock(BrandUpdateRequest request) {
        lockManager.runWithLock(KEY, DEFAULT_LOCK_TTL, () -> update(request));
    }

    private void update(BrandUpdateRequest request) {
        Brand brand = brandQueryService.masterGetById(request.getId());
        if (brand == null) {
            throw new CustomException("品牌信息不存在");
        }

        if (!brand.getName().equals(request.getName())) {
            if (brandQueryService.masterCountByName(request.getName()) > 0) {
                throw new CustomException("品牌名称已存在");
            }
        }

        if (StringUtils.isNotBlank(request.getCertificateCode()) &&
                !request.getCertificateCode().equals(brand.getCertificateCode())) {
            if (brandQueryService.masterCountByCode(request.getCertificateCode()) > 0) {
                throw new CustomException("商标注册证编号已存在");
            }
        }

        request.copyTo(brand);

        brandService.update(brand);
    }
}
