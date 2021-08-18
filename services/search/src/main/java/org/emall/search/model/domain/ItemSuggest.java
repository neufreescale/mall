package org.emall.search.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.core.completion.Completion;

/**
 * @author gaopeng 2021/8/13
 */
@Document(indexName = "item_suggest")
@Data
@NoArgsConstructor
@Setting(settingPath = "setting/item_suggest.json")
@Accessors(chain = true)
public class ItemSuggest {

    @Id
    private String id;

    @CompletionField(analyzer = "pinyin_analyzer", searchAnalyzer = "suggest_search_analyzer", preserveSeparators = false, preservePositionIncrements = false)
    private Completion suggest;
}
