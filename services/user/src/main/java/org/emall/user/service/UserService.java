package org.emall.user.service;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.producer.MqProducer;
import org.diwayou.mq.producer.SendResult;
import org.emall.user.client.dto.UserDto;
import org.emall.user.dao.UserDao;
import org.emall.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author gaopeng 2021/1/21
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private UserDao userDao;

    public void create(User user) {
        Message<UserDto> message = MessageBuilder.withPayload(user.to(UserDto.class))
                .setHeader(MqHeaders.TOPIC, "test")
                .setHeader(MqHeaders.TAG, "tag1")
                .build();

        SendResult sendResult = mqProducer.send(message);
        log.info("{}", sendResult);

        if (user.getId() == 1) {
            throw new RuntimeException("模拟回滚");
        }
    }

    public User get(Long id) {
        Objects.requireNonNull(id);

        return userDao.get(id);
    }
}
