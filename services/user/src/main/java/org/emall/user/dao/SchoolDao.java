package org.emall.user.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.emall.user.model.entity.School;

import java.util.List;

/**
 * @author gaopeng 2021/5/6
 */
public interface SchoolDao {

    @Insert("insert into school (id,name,belong,f985,f211,province,city,town,address,site,phone,content,sync) values (#{id},#{name},#{belong},#{f985},#{f211},#{province},#{city},#{town},#{address},#{site},#{phone},#{content},#{sync})")
    void insert(School school);

    @Select("select * from school where id=#{id}")
    School get(@Param("id") Integer id);

    @Update("update school set sync=#{syncStatus} where id=#{id}")
    void setSync(@Param("id") Integer id, @Param("syncStatus") Integer syncStatus);

    @Select("select * from school where sync=0 order by id asc;")
    List<School> allNoSync();
}
