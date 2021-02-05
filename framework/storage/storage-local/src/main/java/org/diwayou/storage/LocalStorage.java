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
public class LocalStorage implements Storage {

    private static final String NAME = "local";

    private LocalConfig localConfig;

    public LocalStorage() {
        this.localConfig = ConfigHelper.bind(StorageConfigConstants.NAMESPACE, NAME, LocalConfig.class);
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String store(File file, String path) {
        Path result;
        try {
            Path dst = Paths.get(localConfig.getRoot(), path);
            Files.createDirectories(dst);

            result = Files.copy(file.toPath(), dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("存储文件失败", e);
        }

        return result.toFile().getAbsolutePath();
    }
}
