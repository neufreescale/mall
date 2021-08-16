package org.emall.search.controller;

import org.emall.search.manager.ItemSuggestManager;
import org.emall.search.model.response.ItemSuggestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaopeng 2021/8/13
 */
@RestController
@RequestMapping(path = "/public/suggest/item")
public class ItemSuggestController {

    @Autowired
    private ItemSuggestManager itemSuggestManager;

    @GetMapping(path = "/q")
    public ItemSuggestResponse search(@RequestParam(name = "title", required = false) String title) {
        return itemSuggestManager.suggest(title);
    }
}
