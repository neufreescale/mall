package org.diwayou.mq.message;

/**
 * @author gaopeng 2021/2/2
 */
public interface MqHeaders {

    String PREFIX = "mq_";

    String TOPIC = PREFIX + "topic";

    String TAG = PREFIX + "tag";
}
