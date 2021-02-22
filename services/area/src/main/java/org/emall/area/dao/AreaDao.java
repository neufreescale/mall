package org.emall.area.dao;

import org.apache.ibatis.annotations.Select;
import org.emall.area.model.entity.Area;

import java.util.List;

/**
 * @author gaopeng 2021/2/23
 */
public interface AreaDao {

    @Select("select id, code, name, type, parent_id from area where parent_id = #{parentId}")
    List<Area> getByParentId(Integer parentId);

    @Select("select id, code, name, type, parent_id from area where code = #{code}")
    Area getByCode(String code);
}
