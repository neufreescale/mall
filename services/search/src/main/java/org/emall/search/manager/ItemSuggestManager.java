package org.emall.search.manager;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.search.suggest.completion.FuzzyOptions;
import org.emall.search.model.domain.ItemSuggest;
import org.emall.search.model.response.ItemSuggestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.elasticsearch.core.document.DocumentAdapters;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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

            addSuggest("1", "中国大学");
            addSuggest("2", "中国高铁");
            addSuggest("3", "大连教育集团");
        } catch (Exception e) {
            log.warn("初始化item-suggest失败", e);
        }
    }

    public void addSuggest(String id, String title) {
        AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer("ik_smart", title);
        AnalyzeResponse analyzeResponse = restTemplate.execute(client -> client.indices().analyze(request, RequestOptions.DEFAULT));

        Collection<String> tokens = analyzeResponse.getTokens().stream()
                .map(AnalyzeResponse.AnalyzeToken::getTerm)
                .filter(t -> t.length() > 1)
                .distinct()
                .limit(3)
                .collect(Collectors.toList());

        addSuggest(id, title,
                CollectionUtils.permutations(tokens).stream()
                        .map(ss -> StringUtils.join(ss, ""))
                        .collect(Collectors.toSet()));
    }

    public void addSuggest(String id, String title, Set<String> inputs) {
        inputs.add(title);

        addSuggest(id, title, inputs.toArray(new String[0]));
    }

    public void addSuggest(String id, String title, String... inputs) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "id不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "title不能为空");

        ItemSuggest suggest = new ItemSuggest()
                .setId(id)
                .setTitle(title);
        if (ArrayUtils.isEmpty(inputs)) {
            suggest.setSuggest(new Completion(Collections.singletonList(title)));
        } else {
            suggest.setSuggest(new Completion(inputs));
        }

        if (restTemplate.exists(id, ItemSuggest.class)) {
            restTemplate.delete(id, ItemSuggest.class);
        }

        restTemplate.save(suggest);
    }

    public ItemSuggestResponse suggest(String title) {
        FuzzyOptions options = FuzzyOptions.builder()
                .build();
        CompletionSuggestionBuilder s = SuggestBuilders.completionSuggestion("suggest")
                .prefix(title, options);
        final String suggestName = "title-suggest";
        SuggestBuilder b = new SuggestBuilder().addSuggestion(suggestName, s);
        SearchResponse response = restTemplate.suggest(b, ItemSuggest.class);

        List<String> suggest = response.getSuggest().getSuggestion(suggestName).getEntries().stream()
                .flatMap(e -> e.getOptions().stream())
                .map(o -> (CompletionSuggestion.Entry.Option)o)
                .map(CompletionSuggestion.Entry.Option::getHit)
                .map(DocumentAdapters::from)
                .map(d -> restTemplate.getElasticsearchConverter().read(ItemSuggest.class, d))
                .map(ItemSuggest::getTitle)
                .collect(Collectors.toList());

        return new ItemSuggestResponse()
                .setSuggest(suggest);
    }
}
