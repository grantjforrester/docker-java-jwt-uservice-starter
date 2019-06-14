package com.github.grantjforrester.uservice.starter.config;

import com.github.grantjforrester.uservice.starter.web.controller.HelloController;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);
    private static final String SPEC_PATH = "v2/api-docs";
    private static final String UI_PATH = "swagger-ui.html";

    @Value("${swagger.scheme:http}")
    private String swaggerScheme;

    @Value("${swagger.host:localhost:8080}")
    private String swaggerHost;

    @Value("${swagger.basePath:/}")
    private String swaggerBasePath;

    @Bean
    public Docket api() {

        String swaggerPath = String.format("%s://%s%s", swaggerScheme, swaggerHost, swaggerBasePath);
        LOG.info("Swagger API spec available at: " + swaggerPath + SPEC_PATH);
        LOG.info("Swagger UI available at: " + swaggerPath + UI_PATH);

        return new Docket(DocumentationType.SWAGGER_2)
                .protocols(Sets.newHashSet(swaggerScheme))
                .host(swaggerHost)
                .pathProvider(new ConfigurablePathProvider(swaggerBasePath))
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build();
    }

    private static class ConfigurablePathProvider extends AbstractPathProvider {
        private final String path;

        ConfigurablePathProvider(String path) {
            this.path = path;
        }

        @Override
        protected String applicationPath() {
            return path;
        }

        @Override
        protected String getDocumentationPath() {
            return path;
        }
    }
}
