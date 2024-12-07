package sjcu.spring.utube.adapter.response

import sjcu.spring.utube.domain.entities.Category
import sjcu.spring.utube.domain.entities.Utuber
import spock.lang.Specification

class CreateUtuberResponseTest extends Specification {
    def "CreateUtuberResponse 객체 생성"() {
        given:
        def utuber = Utuber.builder()
                .utuberName("utuber1")
                .utuberUrl("utuberUrl")
                .category(Category.builder()
                        .categoryName("category1")
                        .build())
                .build()

        when:
        def result = CreateUtuberResponse.build(utuber)

        then:
        noExceptionThrown()
        result.utuberId() == utuber.utuberId()
    }
}
