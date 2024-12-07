package sjcu.spring.utube.adapter.request

import spock.lang.Specification

class CreateCategoryRequestTest extends Specification {
    def "CreateCategoryRequest 객체 생성"() {
        given:
        def categoryName = "categoryName"

        when:
        def result = CreateCategoryRequest.builder()
                .categoryName(categoryName)
                .build()

        then:
        noExceptionThrown()
        result.categoryName() == categoryName
    }
}
