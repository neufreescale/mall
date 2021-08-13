package org.emall.search.manager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.emall.search.model.domain.Item;
import org.emall.search.model.response.ItemPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/7/27
 */
@Component
@Slf4j
public class ItemSearchManager {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @PostConstruct
    public void init() {
        try {
            IndexOperations indexOperations = restTemplate.indexOps(Item.class);
            if (!indexOperations.exists()) {
                indexOperations.createWithMapping();
                log.info("创建es索引items成功");
            }
        } catch (Exception e) {
            log.warn("创建es索引失败", e);
        }
    }

    public Item get(String id) {

        return restTemplate.get(id, Item.class);
    }

    public List<Item> index(int size) {
        List<Item> result = Lists.newArrayListWithCapacity(size);
        for (int i = 1; i <= size; i++) {
            Item item = new Item()
                    .setId(String.valueOf(i))
                    .setTitle(String.format("iPhone %d 土黄金 好听的故事", i))
                    .setPrice(12 * i);

            result.add(restTemplate.save(item));
        }

        return result;
    }

    public ItemPageResponse search(String title) {
        Criteria criteria = new Criteria("title").contains(title);
        Query query = new CriteriaQuery(criteria);

        SearchHits<Item> hits = restTemplate.search(query, Item.class);

        List<Item> items = hits.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return new ItemPageResponse()
                .setTotal(hits.getTotalHits())
                .setItems(items);
    }

    public void deleteAll() {
        restTemplate.indexOps(Item.class).delete();
    }
}
