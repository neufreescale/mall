package org.diwayou.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.serialize.hessian2.dubbo.Hessian2FactoryInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaopeng 2021/1/29
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
public class DubboAutoConfiguration {

    static {
        // 设置该配置，序列化对象不需要继承java.io.Serializable
        Hessian2FactoryInitializer.getInstance().getSerializerFactory().setAllowNonSerializable(true);
    }
}
