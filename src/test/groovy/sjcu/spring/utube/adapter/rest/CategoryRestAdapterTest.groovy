package sjcu.spring.utube.adapter.rest

import sjcu.spring.utube.adapter.request.CreateCategoryRequest
import sjcu.spring.utube.application.port.in.CategoryInputPort
import sjcu.spring.utube.domain.entities.Category
import spock.lang.Specification

class CategoryRestAdapterTest extends Specification {
    def categoryInputPort = Mock(CategoryInputPort)
    def categoryRestAdapter = new CategoryRestAdapter(categoryInputPort)

    def category = Category.builder()
            .categoryName("categoryName")
            .build()

    def "유튜브 카테고리 생성"() {
        given:
        def request = CreateCategoryRequest.builder()
                .categoryName(category.categoryName())
                .build()

        categoryInputPort.createCategory(_) >> category

        when:
        def result = categoryRestAdapter.createCategory(request)

        then:
        noExceptionThrown()
        result.body.categoryId() == category.categoryId()
        result.body.categoryName() == category.categoryName()
    }

    def "유튜브 카테고리 리스트 조회"() {
        given:
        categoryInputPort.findCategories() >> List.of(category)
        when:
        def result = categoryRestAdapter.findCategories()

        then:
        noExceptionThrown()
        result.body.getFirst().categoryId() == category.categoryId()
    }
}
