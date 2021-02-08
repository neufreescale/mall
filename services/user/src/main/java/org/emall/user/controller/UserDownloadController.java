package org.emall.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.updown.annotation.RequestFile;
import org.diwayou.updown.annotation.ResponseFile;
import org.diwayou.updown.download.DownloadCallback;
import org.emall.user.manager.UserManager;
import org.emall.user.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author gaopeng 2021/2/7
 */
@Controller
@RequestMapping("/user")
@ResponseFile
@Slf4j
public class UserDownloadController {

    @Autowired
    private UserManager userManager;

    @GetMapping("/download")
    public DownloadCallback<UserResponse> downloadUser() {
        return context -> {
            Integer page = (Integer) context.get("page");
            if (page == null) {
                page = 1;
            } else {
                page = page + 1;
            }
            context.set("page", page);

            if (page == 2) {
                context.setFinished();
            }

            return Arrays.asList(new UserResponse(1L, "diwayou"));
        };
    }

    @GetMapping("/download1")
    public Collection<UserResponse> download1User() {
        return Arrays.asList(new UserResponse(1L, "diwayou"));
    }

    @PostMapping("/upload")
    @ResponseBody
    public Collection<UserResponse> upload(@RequestFile(dataClass = UserResponse.class) List<UserResponse> users) {
        for (UserResponse user : users) {
            log.info("{}", user);
        }

        return users;
    }
}
