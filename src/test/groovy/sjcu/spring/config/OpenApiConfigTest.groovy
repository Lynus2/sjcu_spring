package sjcu.spring.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import spock.lang.Specification

class OpenApiConfigTest extends Specification {
    def "springDoc version check"() {
        given:
        def openApiConfig = new OpenApiConfig()

        def springdocVersion = "3.0.1"

        when:
        def result = openApiConfig.openAPI(springdocVersion)

        then:
        result.getInfo().getVersion() == springdocVersion
        noExceptionThrown()
    }


    def "OpenAPI bean should be configured correctly"() {
        given:
        def springdocVersion = "1.0.0"
        def openAPIConfiguration = new OpenAPIConfiguration()

        when:
        OpenAPI result = openAPIConfiguration.openAPI(springdocVersion)

        then:
        result != null
        result.info != null
        result.info.title == "SJCU_SPRING_HOMEWORK"
        result.info.version == springdocVersion
        result.info.description == "SJCU_SPRING_HOMEWORK"
    }

    class OpenAPIConfiguration {
        @Bean
        OpenAPI openAPI(@Value('${springdoc.api-docs.version}') String springdocVersion) {
            Info info = new Info()
                    .title("SJCU_SPRING_HOMEWORK")
                    .version(springdocVersion)
                    .description("SJCU_SPRING_HOMEWORK")

            return new OpenAPI()
                    .info(info)
        }
    }
}
