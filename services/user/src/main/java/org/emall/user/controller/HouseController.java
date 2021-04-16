package org.emall.user.controller;

import org.diwayou.updown.annotation.RequestFile;
import org.emall.user.manager.HouseManager;
import org.emall.user.model.domain.HouseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author gaopeng 2021/4/16
 */
@Controller
@RequestMapping(path = "/house")
public class HouseController {

    @Autowired
    private HouseManager houseManager;

    @PostMapping("/upload")
    @ResponseBody
    public void upload(@RequestFile List<HouseInfo> houseInfos) {
        houseManager.upload(houseInfos);
    }
}
