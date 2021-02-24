package org.emall.brand.api;

import org.emall.brand.dto.BrandDto;
import org.emall.brand.dto.BrandSimpleDto;

import java.util.List;
import java.util.Set;

/**
 * @author gaopeng 2021/2/24
 */
public interface BrandApi {

    BrandDto getById(Integer brandId);

    List<BrandDto> getByIds(Set<Integer> brandIds);

    /**
     * 分页获取品牌信息
     *
     * @param lastId 上次获取的品牌列表最后一个id, 第一次传0
     * @param size 分页大小
     * @return 品牌列表
     */
    List<BrandSimpleDto> pageSimple(Integer lastId, Integer size);
}
