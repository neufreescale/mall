package org.emall.user.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.emall.user.model.entity.House;

/**
 * @author gaopeng 2021/4/16
 */
public interface HouseDao {

    @Insert("insert into house (building_num,location,house_count,unit,floor,house_num,area,inner_area,area_percent) values (#{buildingNum},#{location},#{houseCount},#{unit},#{floor},#{houseNum},#{area},#{innerArea},#{areaPercent})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(House house);
}
