package sjcu.spring.utube.adapter.response

import sjcu.spring.utube.domain.entities.Category
import sjcu.spring.utube.domain.entities.Utuber
import spock.lang.Specification

class FindUtubersResponseTest extends Specification {
    def utuberId = UUID.randomUUID()
    def utuberName = "utuberName"
    def utuberUrl = "utuberUrl"
    def categoryId = UUID.randomUUID()
    def categoryName = "categoryName"
    def isUse = true

    def "FindUtubersResponse 객체 생성"() {
        when:
        def result = FindUtubersResponse.builder()
                .utuberId(utuberId)
                .utuberName(utuberName)
                .utuberUrl(utuberUrl)
                .categoryId(categoryId)
                .categoryName(categoryName)
                .isUse(isUse)
                .build()

        then:
        result.utuberId() == utuberId
        result.utuberName() == utuberName
        result.utuberUrl() == utuberUrl
        result.categoryId() == categoryId
        result.categoryName() == categoryName
        result.isUse() == isUse
    }

    def "List<Utuber>를 List<FindUtubersResponse>로 변환"() {
        given:
        def utuber1 = Utuber.builder()
                .utuberName("utuberName1")
                .utuberUrl("utuberUrl1")
                .category(Category.builder()
                        .categoryName("category1")
                        .build())
                .build()
        def utuber2 = Utuber.builder()
                .utuberName("utuberName2")
                .utuberUrl("utuberUrl2")
                .category(Category.builder()
                        .categoryName("category2")
                        .build())
                .build()
        def utubers = [utuber1, utuber2]

        when:
        def result = FindUtubersResponse.builds(utubers)

        then:
        result.getFirst().utuberName() == utuber1.utuberName()
        result.getFirst().utuberUrl() == utuber1.utuberUrl()
        result.getFirst().utuberId() == utuber1.utuberId()

        result.getLast().utuberName() == utuber2.utuberName()
        result.getLast().utuberUrl() == utuber2.utuberUrl()
        result.getLast().utuberId() == utuber2.utuberId()
    }
}
