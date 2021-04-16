package org.emall.user.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/4/16
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class House {

    private Integer id;

    private Integer buildingNum;

    private String location;

    private Integer houseCount;

    private Integer unit;

    private Integer floor;

    private Integer houseNum;

    private Double area;

    private Double innerArea;

    private Double areaPercent;
}
