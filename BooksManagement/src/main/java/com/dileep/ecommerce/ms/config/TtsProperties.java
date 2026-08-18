package com.dileep.ecommerce.ms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "tts")
public record TtsProperties(
		@NotBlank
        String piperPath,
        @NotBlank
        String modelDirectory,
        @NotBlank
        String defaultVoice
) {
}