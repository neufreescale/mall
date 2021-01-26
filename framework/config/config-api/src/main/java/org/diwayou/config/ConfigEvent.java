package org.diwayou.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author gaopeng 2021/1/19
 */
@Data
@AllArgsConstructor
public class ConfigEvent {

    private final String namespace;

    private final String key;

    private final String value;
}
