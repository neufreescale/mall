package org.emall.search.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.completion.Completion;

/**
 * @author gaopeng 2021/8/13
 */
@Document(indexName = "item_suggest")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ItemSuggest {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String title;

    @CompletionField(analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private Completion suggest;
}
