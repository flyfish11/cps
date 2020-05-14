package com.cloud.txmanager.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Classname SwaggerConfig
 * @Description Swagger配置
 * @Author yulj
 * @Date: 2019/06/29 11:04
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUi
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 对所有api进行监控
                .apis(RequestHandlerSelectors.any())
                // 不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                // 对根下所有路径进行监控
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("分布式事务管理模块接口文档")
                .description("tx-manager Doc")
                .contact(new Contact("yulj", "", "1435240630@qq.com"))
                .version("2.0")
                .build();
    }
}