package br.com.udemy.springexpert.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Vendas API", version = "1.0", description = "Api do projeto de Vendas"))
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("JWT", apiKey())); // Adicionando o esquema de segurança
    }

    private Info apiInfo() {
        return new Info()
                .title("Vendas API")
                .description("Api do projeto de Vendas")
                .version("1.0")
                .contact(new Contact()
                        .name("Mateus Candido")
                        .url("http://github.com/MateusCan")
                        .email("mateuscan42@gmail.com"));
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("vendas")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public SecurityScheme apiKey() {
        return new SecurityScheme()
                .type(Type.APIKEY)
                .name("Authorization")
                .in(SecurityScheme.In.HEADER);
    }
}
