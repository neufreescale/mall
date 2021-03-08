package org.emall.order.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author gaopeng 2021/3/8
 */
@Data
@NoArgsConstructor
public class OrderCreateRequest {

    @NotNull(message = "电话不能为空")
    @Pattern(message = "无效手机号，请重新输入", regexp = "[\\d]{11}")
    private String phone;
}
