package org.diwayou.core.page;

import lombok.Getter;
import lombok.Setter;
import org.diwayou.core.bean.Convertible;

/**
 * @author gaopeng 2021/1/29
 */
@Getter
@Setter
public abstract class PageConvertible implements Convertible {

    private int page = PageConstants.PAGE_DEFAULT_NUM;

    private int rows = PageConstants.PAGE_DEFAULT_SIZE;

    public <R extends PageQuery> R toP(Class<R> targetClass) {
        R r = to(targetClass);

        r.setOffset((getPage() - 1) * getRows());
        r.setSize(Math.min(getRows(), PageConstants.PAGE_MAX_SIZE));

        return r;
    }
}
