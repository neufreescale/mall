package org.diwayou.storage;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/2/5
 */
@NoArgsConstructor
@Data
public class NfsConfig {

    private String root;

    private String urlPrefix;
}
