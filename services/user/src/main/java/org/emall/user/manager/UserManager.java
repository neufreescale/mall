package org.emall.user.manager;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.cache.annotation.Cache;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.producer.MqProducer;
import org.diwayou.mq.producer.SendResult;
import org.diwayou.storage.Storage;
import org.diwayou.storage.StorageFactory;
import org.emall.user.client.dto.UserDto;
import org.emall.user.model.entity.User;
import org.emall.user.model.response.UserResponse;
import org.emall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/2/1
 */
@Component
@Slf4j
public class UserManager {

    @Autowired
    private UserService userService;

    @Autowired
    private MqProducer mqProducer;

    @Cache(key = "user")
    public UserResponse get(Long id) {
        Storage storage = StorageFactory.get("local");

        log.info(storage.name());
        User user = userService.get(id);
        if (user == null) {
            return null;
        }

        userService.create(user);

        Message<UserDto> message = MessageBuilder.withPayload(user.to(UserDto.class))
                .setHeader(MqHeaders.TOPIC, "test")
                .setHeader(MqHeaders.TAG, "tag1")
                .build();
        SendResult sendResult = mqProducer.send(message);
        log.info("发送成功 {}", sendResult);

        return user.to(UserResponse.class);
    }
}
