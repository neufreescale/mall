package org.emall.user.model.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.diwayou.core.bean.Convertible;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
public class SchoolInfo implements Convertible {

    @JsonProperty("school_id")
    @ExcelProperty("学校编号")
    private String id;

    @JsonProperty("name")
    @ExcelProperty("名称")
    private String name;

    @JsonProperty("belong")
    @ExcelProperty("归属")
    private String belong;

    @JsonProperty("f985")
    @ExcelProperty("是否985")
    private String f985;

    @JsonProperty("f211")
    @ExcelProperty("是否211")
    private String f211;

    @JsonProperty("province_name")
    @ExcelProperty("省")
    private String province;

    @JsonProperty("city_name")
    @ExcelProperty("市")
    private String city;

    @JsonProperty("town_name")
    @ExcelProperty("区")
    private String town;

    @JsonProperty("address")
    @ExcelProperty("地址")
    private String address;

    @JsonProperty("site")
    @ExcelProperty("招生网址")
    private String site;

    @JsonProperty("phone")
    @ExcelProperty("电话")
    private String phone;

    @JsonProperty("content")
    @ExcelProperty("简介")
    private String content;
}
