package org.emall.user.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.emall.user.model.entity.Score;

/**
 * @author gaopeng 2021/5/6
 */
public interface ScoreDao {

    @Insert("insert into score (school_id,`name`,batch_name,type_name,`min`,`rank`,`year`) values (#{schoolId},#{name},#{batchName},#{typeName},#{min},#{rank},#{year})")
    void insert(Score score);

    @Delete("delete from score where school_id=#{id} and year=#{year}")
    void delete(@Param("id") Integer id, @Param("year") int year);

    @Delete("delete from score where school_id=#{id}")
    void deleteSchool(@Param("id") Integer id);
}
