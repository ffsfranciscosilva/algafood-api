package com.ffs.algafood.core.openapi;

import com.fasterxml.classmate.TypeResolver;
import com.ffs.algafood.api.exception.model.ApiException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        final var typeResolver = new TypeResolver();

        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ffs.algafood.api"))
                .paths(PathSelectors.any())
                .build()

                .useDefaultResponseMessages(false)
                .globalResponseMessage(GET, globalGetDefaultMessages())
                .globalResponseMessage(POST, globalPostDefaultMessages())
                .globalResponseMessage(PUT, globalPostDefaultMessages())
                .globalResponseMessage(DELETE, globalDeleteDefaultMessages())

                .additionalModels(typeResolver.resolve(ApiException.class))

                .apiInfo(apiInfo());
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("Open API for customers and restaurants")
                .version("0.0.1-SNAPSHOT")
                .contact(new Contact("AlgaFood", "https://www.algafood-domain.com", "contact@algafood-domain.com"))
                .build();
    }

    private List<ResponseMessage> globalGetDefaultMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(NOT_ACCEPTABLE.value()).message("Representation not acceptable")
                        .build(),
                new ResponseMessageBuilder()
                        .code(INTERNAL_SERVER_ERROR.value()).message("Internal server error")
                        .build()
        );
    }

    private List<ResponseMessage> globalPostDefaultMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(BAD_REQUEST.value()).message("Bad Request")
                        .build(),
                new ResponseMessageBuilder()
                        .code(NOT_ACCEPTABLE.value()).message("Representation not acceptable")
                        .build(),
                new ResponseMessageBuilder()
                        .code(UNSUPPORTED_MEDIA_TYPE.value()).message("Media type not supported")
                        .build(),
                new ResponseMessageBuilder()
                        .code(INTERNAL_SERVER_ERROR.value()).message("Internal server error")
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteDefaultMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(BAD_REQUEST.value()).message("Bad Request")
                        .build(),
                new ResponseMessageBuilder()
                        .code(INTERNAL_SERVER_ERROR.value()).message("Internal server error")
                        .build()
        );
    }
}