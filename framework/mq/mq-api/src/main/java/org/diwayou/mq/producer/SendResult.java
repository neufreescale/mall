package org.diwayou.mq.producer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/2/2
 */
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class SendResult {

    private String topic;

    private String msgId;
}
