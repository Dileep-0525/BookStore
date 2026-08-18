package com.dileep.ecommerce.ms.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.onnxruntime.OrtEnvironment;

@Configuration
public class OnnxConfiguration {

    @Bean
    public OrtEnvironment ortEnvironment() {
        return OrtEnvironment.getEnvironment();
    }

}
