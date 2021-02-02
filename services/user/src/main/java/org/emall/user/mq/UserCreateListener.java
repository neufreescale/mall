package org.emall.user.mq;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.mq.annotation.MqListener;
import org.diwayou.mq.message.MqHeaders;
import org.emall.user.client.dto.UserDto;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng 2021/2/2
 */
@Component
@Slf4j
public class UserCreateListener {

    @MqListener(topic = "test", tag = "tag2")
    public void handle(@Payload UserDto userDto, @Header(MqHeaders.TOPIC) String topic) {
        log.info("{}, {}", topic, userDto);
    }

    @MqListener(topic = "test", tag = "tag1")
    public void handleTag(@Payload UserDto userDto, @Header(MqHeaders.TOPIC) String topic,
                          @Header(value = MqHeaders.TAG, required = false) String tag) {
        log.info("{}, {}, {}", topic, tag, userDto);
    }
}
