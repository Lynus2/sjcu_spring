package sjcu.spring.utube.application.port.in.command

import spock.lang.Specification

class CreateUtuberCommandTest extends Specification {
    def "CreateUtuberCommand 객체 생성"() {
        given:
        def utuberName = "utuberName"
        def utuberUrl = "utuberUrl"
        def categoryId = UUID.randomUUID()

        when:
        def result = CreateUtuberCommand.builder()
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
