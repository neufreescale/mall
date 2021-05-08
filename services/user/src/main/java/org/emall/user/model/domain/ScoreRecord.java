package org.emall.user.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.diwayou.core.bean.Convertible;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
public class ScoreRecord implements Convertible {

    @JsonIgnore
    private Integer id;

    @JsonProperty("school_id")
    private Integer schoolId;

    @JsonProperty("spname")
    private String name;

    @JsonProperty("local_batch_name")
    private String batchName;

    @JsonProperty("local_type_name")
    private String typeName;

    @JsonProperty("min")
    private String min;

    @JsonProperty("average")
    private String average;

    @JsonProperty("min_section")
    private String rank;

    @JsonProperty("year")
    private Integer year;
}
