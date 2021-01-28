package org.diwayou.core.yml;

import com.google.common.base.Strings;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.Properties;

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

    public static Properties unmarshalProperties(final String yamlContent) {
        return Strings.isNullOrEmpty(yamlContent) ? new Properties() : new Yaml().loadAs(yamlContent, Properties.class);
    }
}
