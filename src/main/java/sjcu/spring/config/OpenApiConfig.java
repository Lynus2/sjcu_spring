package sjcu.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.api-docs.version}") String springdocVersion) {
        Info info = new Info()
                .title("SJCU_SPRING_HOMEWORK")
                .version(springdocVersion)
                .description("SJCU_SPRING_HOMEWORK");

        return new OpenAPI()
                .info(info);
    }
}