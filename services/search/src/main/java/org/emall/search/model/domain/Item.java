package org.emall.search.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author gaopeng 2021/7/27
 */
@Document(indexName = "items")
//@Mapping(mappingPath = "mapping/item.json")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Item {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Integer)
    private Integer price;
}
