package org.emall.user.model.response;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/2/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @ExcelProperty("用户id")
    private Long id;

    @ExcelProperty("用户姓名")
    private String name;
}
