package org.emall.user.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.emall.user.model.entity.Score;
import org.emall.user.model.response.ScoreResponse;

import java.util.Collection;

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

    @Select("select sl.name as scName,sc.name as spName,sc.`min` as score,sc.`rank` as sort,sc.year from score sc inner join school sl on sc.school_id=sl.id where year = #{year} order by `rank` asc;")
    Collection<ScoreResponse> rank(@Param("year") Integer year);
}
