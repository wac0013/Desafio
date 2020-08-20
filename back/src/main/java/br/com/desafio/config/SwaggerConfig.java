package br.com.desafio.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	@Bean
	public Docket apiDoc() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.desafio"))
				.build()
				.securitySchemes(Arrays.asList(new ApiKey("Token de Acesso", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
				.securityContexts(Arrays.asList(securityContext()))
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Desafio API").description("Documentação da API utilizando Swagger")
				.version("1.0.0").contact(new Contact("Wellington Alves Costa","https://wac0013.github.io/mini-curriculo/", "wac.0013@gmail.com"))
				.build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.ant("/evento/**"))
				.build();
	}

	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration(
			null,
			null,
			null, // realm Needed for authenticate button to work
			null, // appName Needed for authenticate button to work
			"BEARER ",// apiKeyValue
			ApiKeyVehicle.HEADER,
			"AUTHORIZATION", //apiKeyName
			null);
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authScope = new AuthorizationScope("ADMIN", "acesso ilimitado");
		AuthorizationScope[] authScopes = new AuthorizationScope[1];
		authScopes[0] = authScope;
		return Arrays.asList(new SecurityReference("Token de Acesso", authScopes));
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/docs/swagger-ui.html");
		registry.addRedirectViewController("/docs/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
		registry.addRedirectViewController("/docs/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
		registry.addRedirectViewController("/docs/swagger-resources/configuration/security","/swagger-resources/configuration/security");
		registry.addRedirectViewController("/docs/swagger-resources", "/swagger-resources");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/META-INF/resources/");
	}
	
}