package org.emall.user.mq;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.mq.annotation.MqListener;
import org.diwayou.mq.message.MqHeaders;
import org.diwayou.mq.transaction.MqCheckListener;
import org.diwayou.mq.transaction.TransactionCheckState;
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

    @MqListener(topic = "test")
    public void handle(@Payload UserDto userDto, @Header(MqHeaders.TOPIC) String topic) {
        log.info("{}, {}", topic, userDto);
    }

    @MqCheckListener(topic = "test")
    public TransactionCheckState check(@Payload UserDto userDto, @Header(MqHeaders.TOPIC) String topic,
                                       @Header(value = MqHeaders.TAG, required = false) String tag) {
        log.info("{}, {}, {}", topic, tag, userDto);

        return TransactionCheckState.ROLLBACK;
    }
}
