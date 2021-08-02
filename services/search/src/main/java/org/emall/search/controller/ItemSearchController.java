package org.emall.search.controller;

import org.emall.search.manager.ItemSearchManager;
import org.emall.search.model.domain.Item;
import org.emall.search.model.response.ItemPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gaopeng 2021/7/27
 */
@RestController
@RequestMapping(path = "/public/search/item")
public class ItemSearchController {

    @Autowired
    private ItemSearchManager itemSearchManager;

    @GetMapping(path = "/{id}")
    public Item get(@PathVariable(name = "id") String id) {
        return itemSearchManager.get(id);
    }

    @GetMapping(path = "/index")
    public Item index() {
        return itemSearchManager.index();
    }

    @GetMapping(path = "/q")
    public ItemPageResponse search(@RequestParam(name = "title", required = false) String title) {
        return itemSearchManager.search(title);
    }

    @GetMapping(path = "/deleteAll")
    public void deleteAll() {
        itemSearchManager.deleteAll();
    }
}
