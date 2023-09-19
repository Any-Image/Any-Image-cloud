package com.icloud.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author BinG-W
 * @package_name cloud_image
 * @data 2023/1/9 14:13
 */
@Configurable
public class WebConfig implements WebMvcConfigurer {
    @Value("${file-save-path}")
    private String fileSavePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadFile/**")
                .addResourceLocations("file:"+fileSavePath);
    }
}
