package org.diwayou.config;

/**
 * @author gaopeng 2021/3/9
 */
public interface Environment {

    Env env();

    default boolean isDev() {
        return Env.Dev.equals(env());
    }

    default boolean isTest() {
        return Env.Test.equals(env());
    }

    default boolean isPre() {
        return Env.Pre.equals(env());
    }

    default boolean isOnline() {
        return Env.Online.equals(env());
    }
}
