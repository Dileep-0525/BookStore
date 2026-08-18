package com.dileep.ecommerce.ms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "file.upload")
@Getter
@Setter
public class FileStorageProperties {

    private String path;

}
