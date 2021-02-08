package org.diwayou.updown;

import org.diwayou.updown.download.DownloadReturnValueHandler;
import org.diwayou.updown.upload.UploadArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author gaopeng 2021/2/7
 */
@Configuration(proxyBeanMethods = false)
public class UpdownAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new DownloadReturnValueHandler());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UploadArgumentResolver());
    }
}
