package org.diwayou.storage;

import java.io.File;

/**
 * @author gaopeng 2021/2/5
 */
public interface Storage {

    String name();

    /**
     * 保存文件到对应路径
     *
     * @param file 要保存的文件
     * @param path 保存的路径
     * @return 存储路径
     */
    String store(File file, String path);
}
