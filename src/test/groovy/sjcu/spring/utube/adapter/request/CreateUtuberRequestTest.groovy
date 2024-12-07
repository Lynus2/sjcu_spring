package sjcu.spring.utube.adapter.request

import spock.lang.Specification

class CreateUtuberRequestTest extends Specification {
    def "CreateUtuberRequest 객체 생성"() {
        given:
        def utuberName = "utuberName"
        def utuberUrl = "utuberUrl"
        def categoryId = UUID.randomUUID()

        when:
        def result = CreateUtuberRequest.builder()
                .utuberName(utuberName)
                .utuberUrl(utuberUrl)
                .categoryId(categoryId)
                .build()

        then:
        result.utuberName() == utuberName
        result.utuberUrl() == utuberUrl
        result.categoryId() == categoryId
    }
}
