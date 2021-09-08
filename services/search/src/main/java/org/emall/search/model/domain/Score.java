package org.emall.search.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.*;

/**
 * @author gaopeng 2021/9/7
 */
@Document(indexName = "score")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Score {

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = @InnerField(suffix = "raw", type = FieldType.Keyword))
    private String scName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String spName;

    @Field(type = FieldType.Integer)
    private Integer score;

    @Field(type = FieldType.Integer)
    private Integer sort;

    @Field(type = FieldType.Keyword)
    private Integer year;
}
