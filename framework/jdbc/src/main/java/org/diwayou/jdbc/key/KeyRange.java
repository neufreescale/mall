package org.diwayou.jdbc.key;

import lombok.Getter;
import lombok.ToString;
import org.diwayou.jdbc.exception.GenerateKeyException;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author gaopeng 2021/1/20
 */
@ToString
class KeyRange {

    private final long min;

    private final long max;

    private final long maxSize;

    private final AtomicLong value;

    @Getter
    private volatile boolean overflow = false;

    public KeyRange(long min, long max, long maxSize) {
        this.min = min;
        this.max = max;
        this.maxSize = maxSize;
        this.value = new AtomicLong(min);
    }

    public long next() {
        long cur = value.getAndIncrement();
        if (cur > max) {
            overflow = true;

            return -1;
        }

        return cur;
    }

    public long next(int size) {
        if (size > maxSize) {
            throw new GenerateKeyException("size超过最大可生成长度");
        }

        long cur = value.getAndAdd(size) + size - 1;
        if (cur > max) {
            overflow = true;

            return -1;
        }

        return cur;
    }
}
