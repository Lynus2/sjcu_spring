package sjcu.spring.utube.domain.entities

import sjcu.spring.utube.adapter.repository.CategoryRepository
import spock.lang.Specification

class CategoryTest extends Specification {
    def categoryRepository = Mock(CategoryRepository)

    def "Category 객체 생성"() {
        given:
        def categoryName = "유튜브 카테고리1"

        when:
        def category = Category.builder()
                .categoryName(categoryName)
                .build()

        then:
        noExceptionThrown()
        category.categoryId() == null
        category.categoryName() == categoryName
    }

    def "Category 객체가 저장될 때 ID를 생성하여 저장한다."() {
        given:
        def categoryName = "유튜브 카테고리1"
        def category = Category.builder()
                .categoryName(categoryName)
                .build()
        categoryRepository.save(_) >> { Category c ->
            c.generateUUID()
            return c
        }

        when:
        categoryRepository.save(category)

        then:
        noExceptionThrown()
        category.categoryId() != null
        category.categoryId().getClass() == UUID
        category.categoryName() == categoryName
    }
}
