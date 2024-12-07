package sjcu.spring.utube.adapter.response

import sjcu.spring.utube.domain.entities.Category
import spock.lang.Specification

class FindCategoriesResponseTest extends Specification {
    def "List<FindCategoriesResponse> 객체 생성"() {
        given:
        def category = Category.builder()
                .categoryName("categoryName")
                .build()
        def categories = List.of(category)

        when:
        def result = FindCategoriesResponse.build(categories)

        then:
        result.getFirst().categoryId() == category.categoryId()
        result.getFirst().categoryName() == category.categoryName()
    }
}
