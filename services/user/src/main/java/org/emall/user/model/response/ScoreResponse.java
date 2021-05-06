package org.emall.user.model.response;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
public class ScoreResponse {

    @ExcelProperty("学校")
    private String scName;

    @ExcelProperty("专业")
    private String spName;

    @ExcelProperty("最低分")
    private Integer score;

    @ExcelProperty("排名")
    private Integer sort;

    @ExcelProperty("年份")
    private Integer year;
}
