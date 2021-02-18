package org.diwayou.mq.memory.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;

/**
 * @author gaopeng 2021/2/18
 */
@RequiredArgsConstructor
@Getter
public class MemoryMqMessage {

    private final Message<?> message;
}
