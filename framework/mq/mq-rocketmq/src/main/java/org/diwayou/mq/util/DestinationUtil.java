package org.diwayou.mq.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gaopeng 2021/2/3
 */
public final class DestinationUtil {

    public static String generate(String topic, String tag) {
        if (StringUtils.isBlank(tag)) {
            return topic;
        }

        return topic + ":" + tag;
    }
}
