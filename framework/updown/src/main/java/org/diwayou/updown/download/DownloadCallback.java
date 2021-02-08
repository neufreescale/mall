package org.diwayou.updown.download;

import java.util.List;

/**
 * @author gaopeng 2021/2/8
 */
public interface DownloadCallback<T> {

    List<T> execute(DownloadContext context);
}
