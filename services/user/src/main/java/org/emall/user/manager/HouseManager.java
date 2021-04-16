package org.emall.user.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.emall.user.model.domain.HouseInfo;
import org.emall.user.model.entity.House;
import org.emall.user.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gaopeng 2021/4/16
 */
@Component
@Slf4j
public class HouseManager {

    @Autowired
    private HouseService houseService;

    public void upload(List<HouseInfo> houseInfos) {
        for (HouseInfo info : houseInfos) {
            House house = parse(info);

            log.info("{}", house);
            houseService.insert(house);
        }
    }

    private House parse(HouseInfo info) {
        int buildingNum = Integer.parseInt(StringUtils.removeEnd(info.getNum(), "号楼"));
        int houseCount = Integer.parseInt(StringUtils.removeEnd(info.getHouseCount(), "套"));
        String[] level = info.getLevel().split("-+");
        int unit = Integer.parseInt(level[0]);
        int floor = Integer.parseInt(level[1]);
        int houseNum = Integer.parseInt(StringUtils.removeEnd(level[2], " （跃）"));
        double area = Double.parseDouble(info.getArea());
        double innerArea = Double.parseDouble(info.getInnerArea());
        double areaPercent = Double.parseDouble(info.getAreaPercent());

        return new House()
                .setBuildingNum(buildingNum)
                .setLocation(info.getLocation())
                .setHouseCount(houseCount)
                .setUnit(unit)
                .setFloor(floor)
                .setHouseNum(houseNum)
                .setArea(area)
                .setInnerArea(innerArea)
                .setAreaPercent(areaPercent);
    }
}
