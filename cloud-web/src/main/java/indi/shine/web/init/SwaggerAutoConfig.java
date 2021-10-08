package indi.shine.web.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiezhenxiang 2021/9/13
 */
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfig {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${boot.base.version}")
    private String baseVersion;
    @Value("${swagger.auth:false}")
    private Boolean swaggerAuth;
    @Value("${swagger.base.package}")
    private String basePackage;
    @Bean
    public Docket api() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .description("the documents of "+ appName +"'s restful apis")
                .contact("admin")
                .version(baseVersion)
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName(appName)
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.swagger.bootstrap.ui.demo.controller"))
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
        if (swaggerAuth) {
            docket.securityContexts(defaultSecurityContext());
            docket.securitySchemes(defaultSecuritySchemes());
        }
        return docket;
    }

    private List<ApiKey> defaultSecuritySchemes() {

        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeys;
    }

    private List<SecurityContext> defaultSecurityContext() {

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));

        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReferences)
                .forPaths(PathSelectors.regex("/.*"))
                .build());
        return securityContexts;
    }
}
