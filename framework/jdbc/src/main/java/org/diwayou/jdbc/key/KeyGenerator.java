package org.diwayou.jdbc.key;

import org.diwayou.jdbc.exception.GenerateKeyException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gaopeng 2021/1/20
 */
public class KeyGenerator {

    private KeyGeneratorDao keyGeneratorDao;

    private String name;

    private final Lock lock = new ReentrantLock();

    private volatile KeyRange curRange;

    public KeyGenerator(KeyGeneratorDao keyGeneratorDao, String name) {
        this.keyGeneratorDao = keyGeneratorDao;
        this.name = name;
    }

    public long next() {
        if (curRange == null) {
            lock.lock();
            try {
                if (curRange == null) {
                    curRange = keyGeneratorDao.nextRange(name);
                }
            } finally {
                lock.unlock();
            }
        }

        long value = curRange.next();
        if (value == -1) {
            lock.lock();
            try {
                for (; ; ) {
                    if (curRange.isOverflow()) {
                        curRange = keyGeneratorDao.nextRange(name);
                    }

                    value = curRange.next();
                    if (value == -1) {
                        continue;
                    }

                    break;
                }
            } finally {
                lock.unlock();
            }
        }

        if (value < 0) {
            throw new GenerateKeyException("生成key失败, value = " + value);
        }

        return value;
    }

    public long next(int size) {
        if (curRange == null) {
            lock.lock();
            try {
                if (curRange == null) {
                    curRange = keyGeneratorDao.nextRange(name);
                }
            } finally {
                lock.unlock();
            }
        }

        long value = curRange.next(size);
        if (value == -1) {
            lock.lock();
            try {
                for (; ; ) {
                    if (curRange.isOverflow()) {
                        curRange = keyGeneratorDao.nextRange(name);
                    }

                    value = curRange.next(size);
                    if (value == -1) {
                        continue;
                    }

                    break;
                }
            } finally {
                lock.unlock();
            }
        }

        if (value < 0) {
            throw new GenerateKeyException("生成key失败, value = " + value);
        }

        return value;
    }
}
