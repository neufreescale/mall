package org.emall.brand.dao;

import org.apache.ibatis.annotations.*;
import org.emall.brand.dto.BrandSimpleDto;
import org.emall.brand.model.domain.BrandListQuery;
import org.emall.brand.model.domain.BrandSimple;
import org.emall.brand.model.entity.Brand;

import java.util.List;

/**
 * @author gaopeng 2021/2/24
 */
public interface BrandDao {

    @Insert("insert into brand (name,logo,certificate_code,`desc`,status,create_time) values (#{name},#{logo},#{certificateCode},#{desc},1,NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Brand brand);

    @Select("select count(*) from brand where name=#{name} and status=1")
    int countByName(@Param("name") String name);

    @Select("select count(*) from brand where certificate_code=#{code} and status=1")
    int countByCode(@Param("code") String code);

    @Select("select id,name,logo,certificate_code,`desc` from brand where id=#{id}")
    Brand getById(@Param("id") Integer id);

    @Update("update brand set name=#{name},logo=#{logo},certificate_code=#{certificateCode},`desc`=#{desc},modify_time=NOW() where id=#{id}")
    int update(Brand brand);

    Integer listCount(BrandListQuery query);

    List<Brand> list(BrandListQuery query);

    @Select("select name from brand where id=#{id}")
    String getName(Integer id);

    @Select("select logo from brand where id=#{id}")
    String getLogo(Integer id);

    @Select("select id from brand where name=#{name} and status=1")
    Integer getIdByName(String name);

    @Select("select id,name from brand where id=#{id}")
    BrandSimple getSimple(Integer id);

    @Select("select id,name from brand where id > #{lastId} and status=1 order by id asc limit #{size}")
    List<BrandSimpleDto> pageSimple(@Param("lastId") Integer lastId, @Param("size") Integer size);
}
