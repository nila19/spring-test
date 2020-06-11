package com.springtest.spring.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@OpenAPIDefinition(info = @Info(title = "the title", version = "v1", description = "My API",
// license = @License(name = "Apache 2.0", url = "http://foo.bar"), contact = @Contact(url =
// "http://gigantic-server.com", name = "Fred", email = "Fred@gigagantic-server.com")))
public class SwaggerConfig {
  @Bean
  public OpenAPI springTestOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("SpringTest App")
            .description("Spring test application")
            .version("v0.0.1")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation()
            .description("SpringTest Documentation")
            .url("https://springshop.wiki.github.org/docs"));
  }
}
