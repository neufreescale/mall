package org.emall.user.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class School {

    private Integer id;

    private String name;

    private String belong;

    private Boolean f985;

    private Boolean f211;

    private String province;

    private String city;

    private String town;

    private String address;

    private String site;

    private String phone;

    private String content;

    private Integer sync;
}
