package org.diwayou.storage;

import org.diwayou.config.ConfigHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author gaopeng 2021/2/5
 */
public class NfsStorage implements Storage {

    private static final String NAME = "nfs";

    private NfsConfig nfsConfig;

    public NfsStorage() {
        this.nfsConfig = ConfigHelper.bind(StorageConfigConstants.NAMESPACE, NAME, NfsConfig.class);
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String store(File file, String path) {
        Path dstPath;
        try {
            Path dst = Paths.get(nfsConfig.getRoot(), path);
            Files.createDirectories(dst);

            dstPath = Files.copy(file.toPath(), dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("存储文件失败", e);
        }

        return nfsConfig.getUrlPrefix() + dstPath;
    }
}
