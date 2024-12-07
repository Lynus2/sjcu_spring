package sjcu.spring.utube.application.service

import sjcu.spring.utube.adapter.request.CreateCategoryRequest
import sjcu.spring.utube.application.port.out.CategoryOutputPort
import sjcu.spring.utube.domain.entities.Category
import spock.lang.Specification

class CategoryServiceTest extends Specification {
    def categoryOutputPort = Mock(CategoryOutputPort)
    def categoryService = new CategoryService(categoryOutputPort)

    def "Category를 생성한다"() {
        given:
        def request = CreateCategoryRequest.builder()
                .categoryName("categoryName")
                .build()
        categoryOutputPort.save(_) >> new Category(request.categoryName())

        when:
        def result = categoryService.createCategory(request)

        then:
        noExceptionThrown()
        result.categoryName() == request.categoryName()
    }

    def "모든 Category 리스트를 조회한다"() {
        given:
        categoryOutputPort.findAll() >> List.of(
                Category.builder()
                        .categoryName("categoryName1")
                        .build(),
                Category.builder()
                        .categoryName("categoryName2")
                        .build()
        )

        when:
        def result = categoryService.findCategories()

        then:
        noExceptionThrown()
        result.size() == 2
    }
}
