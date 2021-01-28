package org.emall.user.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/1/29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto {

    private Long id;

    private String name;
}
