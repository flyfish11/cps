package com.cloud.user.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @Classname FeignInterceptorConfig
 * @Description 使用FeignClient访问别的微服务时将token放入header进行传递，解决FeignClient调用401问题
 * @Author yulj
 * @Date: 2019/12/03 4:57 下午
 */
@Configuration
public class FeignInterceptorConfig
{

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if ( authentication instanceof OAuth2Authentication) {

                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                template.header("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + details.getTokenValue());

            }
        };


    }
}
