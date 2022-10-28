package com.buslinespoa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.buslinespoa.controller"))
			.paths(PathSelectors.ant("/api/*/*"))
			.build()
			.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		return new ApiInfoBuilder()
			.title("PI REST BusLine POA")
			.description("API REST for linhas de ônibus de Porto Alegre")
			.version("1.0.0")
			.contact(new Contact("Rodrigo Gambarra", "https://github.com/rodrigogambarra/Api-Restful-Bus-Lines-POA", "rodrigo@gambarra.com.br"))
			.build();
	}
}
