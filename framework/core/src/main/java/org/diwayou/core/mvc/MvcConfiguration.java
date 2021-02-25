package org.diwayou.core.mvc;

import org.diwayou.core.aop.BaseController;
import org.diwayou.core.aop.ResponseBodyWrapper;
import org.diwayou.core.controller.CustomErrorController;
import org.diwayou.core.json.JacksonCustomizer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author gaopeng 2021/1/15
 */
@Configuration
@Import({SwaggerConfiguration.class})
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class MvcConfiguration {

    private static final DataSize MAX_UPLOAD_SIZE = DataSize.ofMegabytes(20);

    private static final DataSize MAX_UPLOAD_SIZE_ALL = DataSize.ofMegabytes(20 * 15);

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize(MAX_UPLOAD_SIZE);
        // 设置总上传数据总大小
        factory.setMaxRequestSize(MAX_UPLOAD_SIZE_ALL);
        return factory.createMultipartConfig();
    }

    @Bean
    public ResponseBodyWrapper responseBodyWrapper() {
        return new ResponseBodyWrapper();
    }

    @Bean
    public BaseController baseController() {
        return new BaseController();
    }

    @Bean
    public ErrorController customError() {
        return new CustomErrorController();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return new JacksonCustomizer();
    }
}
