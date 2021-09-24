package org.emall.search.controller;

import org.emall.search.manager.ScoreSearchManager;
import org.emall.search.model.response.ScorePageResponse;
import org.emall.search.model.response.ScoreStatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gaopeng 2021/9/7
 */
@RestController
@RequestMapping(path = "/public/search/score")
public class ScoreSearchController {

    @Autowired
    private ScoreSearchManager scoreSearchManager;

    @GetMapping(path = "/index")
    public void index() {
        scoreSearchManager.index();
    }

    @GetMapping(path = "/q")
    public ScorePageResponse search(@RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "m", required = false, defaultValue = "0") int minSort,
                                    @RequestParam(name = "n", required = false, defaultValue = "25000") int maxSort) {
        return scoreSearchManager.search(name, minSort, maxSort);
    }

    @GetMapping(path = "/stat")
    public List<ScoreStatResponse> stat(@RequestParam(name = "i", required = false, defaultValue = "200") int interval,
                                        @RequestParam(name = "m", required = false, defaultValue = "0") int minSort,
                                        @RequestParam(name = "n", required = false, defaultValue = "25000") int maxSort) {
        return scoreSearchManager.stat(minSort, maxSort, interval);
    }
}
