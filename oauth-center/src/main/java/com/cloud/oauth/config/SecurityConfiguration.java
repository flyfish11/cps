package com.cloud.oauth.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class SecurityConfiguration {
    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.CHINESE);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages");
        return messageSource;
    }
}