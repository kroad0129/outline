package com.outline.outline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Docker 컨테이너 내부에서 /uploads 디렉토리를 정적 자원으로 제공
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/uploads/");
    }
}
