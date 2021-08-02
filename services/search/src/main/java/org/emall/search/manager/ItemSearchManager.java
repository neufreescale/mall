package org.emall.search.manager;

import org.emall.search.model.domain.Item;
import org.emall.search.model.response.ItemPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/7/27
 */
@Component
public class ItemSearchManager {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    public Item get(String id) {

        return restTemplate.get(id, Item.class);
    }

    public Item index() {
        Item item = new Item()
                .setId("1")
                .setTitle("iPhone 13 土黄金")
                .setPrice(12000);

        return restTemplate.save(item);
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
