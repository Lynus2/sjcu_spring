package sjcu.spring.utube.adapter.response

import sjcu.spring.utube.domain.entities.Category
import spock.lang.Specification

class CreateCategoryResponseTest extends Specification {
    def "CreateCategoryResponse 객체 생성"() {
        given:
        def category = Category.builder()
                .categoryName("categoryName")
                .build()

        when:
        def result = CreateCategoryResponse.build(category)

        then:
        result.categoryId() == category.categoryId()
        result.categoryName() == category.categoryName()
    }
}
