package org.emall.search.manager;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.emall.search.model.domain.ItemSuggest;
import org.emall.search.model.response.ItemSuggestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/8/13
 */
@Component
@Slf4j
public class ItemSuggestManager {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @PostConstruct
    public void init() {
        try {
            IndexOperations indexOperations = restTemplate.indexOps(ItemSuggest.class);
            if (!indexOperations.exists()) {
                indexOperations.createWithMapping();
                log.info("创建es索引item-suggest成功");
            }

            String[] keywords = new String[] {
                    "iphone 13",
                    "手机壳",
                    "AirPods",
                    "耐克鞋",
                    "笔记本apple",
                    "笔记本联想"
            };

            for (int i = 0; i < keywords.length; i++) {
                addSuggest(String.valueOf(i + 1), Collections.singletonList(keywords[i]));
            }
        } catch (Exception e) {
            log.warn("初始化item-suggest失败", e);
        }
    }

    public void addSuggest(String id, List<String> inputs) {
        addSuggest(id, inputs, null);
    }

    public void addSuggest(String id, List<String> inputs, Integer weight) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "id不能为空");
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(inputs), "inputs不能为空");

        Completion completion = new Completion(inputs);
        completion.setWeight(weight);
        ItemSuggest suggest = new ItemSuggest()
                .setId(id)
                .setSuggest(completion);

        restTemplate.save(suggest);
    }

    public ItemSuggestResponse suggest(String title) {
        CompletionSuggestionBuilder s = SuggestBuilders.completionSuggestion("suggest")
                .prefix(title, Fuzziness.AUTO)
                .size(10);
        final String suggestName = "title-suggest";
        SuggestBuilder b = new SuggestBuilder().addSuggestion(suggestName, s);
        SearchResponse response = restTemplate.suggest(b, ItemSuggest.class);

        List<String> suggest = response.getSuggest().getSuggestion(suggestName).getEntries().stream()
                .flatMap(e -> e.getOptions().stream())
                .map(o -> o.getText().string())
                .collect(Collectors.toList());

        return new ItemSuggestResponse()
                .setSuggest(suggest);
    }
}
