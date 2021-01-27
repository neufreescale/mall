package org.diwayou.core.yml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * @author gaopeng 2021/1/27
 */
public class YamlUtil {

    public static <T> T unmarshal(final String yamlContent, final Class<T> classType) {
        return new Yaml(new Constructor(classType)).loadAs(yamlContent, classType);
    }

    public static String marshal(final Object value) {
        return new Yaml().dumpAsMap(value);
    }
}
