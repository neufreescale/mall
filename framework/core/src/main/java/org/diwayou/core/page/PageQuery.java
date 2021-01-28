package org.diwayou.core.page;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gaopeng 2021/1/29
 */
@Getter
@Setter
public abstract class PageQuery {

    private int offset;

    private int size;
}
