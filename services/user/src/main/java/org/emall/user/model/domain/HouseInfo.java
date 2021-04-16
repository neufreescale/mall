package org.emall.user.model.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/4/16
 */
@Data
@NoArgsConstructor
public class HouseInfo {


    /**
     * 楼号
     */
    @ExcelProperty("楼号")
    private String num;

    /**
     * 楼位置
     */
    @ExcelProperty("楼位置")
    private String location;

    /**
     * 总套数
     */
    @ExcelProperty("总套数")
    private String houseCount;

    /**
     * 1-1-1代表1单元2楼1门
     */
    @ExcelProperty("楼层")
    private String level;

    /**
     * 房子面积
     */
    @ExcelProperty("房子面积")
    private String area;

    /**
     * 套内面积
     */
    @ExcelProperty("套内面积")
    private String innerArea;

    /**
     * 得房率
     */
    @ExcelProperty("得房率")
    private String areaPercent;
}
