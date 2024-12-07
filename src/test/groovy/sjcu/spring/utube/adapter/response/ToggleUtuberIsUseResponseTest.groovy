package sjcu.spring.utube.adapter.response

import sjcu.spring.utube.domain.entities.Category
import sjcu.spring.utube.domain.entities.Utuber
import spock.lang.Specification

class ToggleUtuberIsUseResponseTest extends Specification {
    def "ToggleUtuberIsUseResponse 객체 생성"() {
        given:
        def utuber = Utuber.builder()
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .category(Category.builder()
                        .categoryName("category")
                        .build())
                .build()

        when:
        def result = ToggleUtuberIsUseResponse.build(utuber)

        then:
        result.utuberId() == utuber.utuberId()
        result.isUse() == utuber.isUse()
    }
}
