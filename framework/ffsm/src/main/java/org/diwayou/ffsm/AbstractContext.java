package org.diwayou.ffsm;

import lombok.Data;

/**
 * @author gaopeng 2021/11/2
 */
@Data
public abstract class AbstractContext<S, E> {

    private S from;

    private S to;

    private E event;
}
