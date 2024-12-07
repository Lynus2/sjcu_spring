package sjcu.spring.utube.adapter.response

import sjcu.spring.utube.domain.entities.Category
import sjcu.spring.utube.domain.entities.Utube
import sjcu.spring.utube.domain.entities.Utuber
import spock.lang.Specification

class UpdateShownUtubeResponseTest extends Specification {
    def "시청한 유튜브 이력을 기록"() {
        given:
        def utuber = Utuber.builder()
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .category(Category.builder()
                        .categoryName("category")
                        .build())
                .build()

        def utube = new Utube(
                UUID.randomUUID(),
                "utubeTitle1",
                "utubeThumbnail1",
                "utubeUrl1",
                false,
                null)
        def utubes = new ArrayList()
        utubes.add(utube)
        utuber.utubes = utubes

        when:
        def result = UpdateShownUtubeResponse.build(utuber, utube.utubeId())

        then:
        noExceptionThrown()
        result.utuberId() == utuber.utuberId()
        result.utubeId() == utube.utubeId()
        result.isShown() == utube.isShown()
    }

    def "시청한 유튜브 이력을 기록시 해당 Utube가 없으면 에러 반환"() {
        given:
        def utuber = Utuber.builder()
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .category(Category.builder()
                        .categoryName("category")
                        .build())
                .build()

        when:
        UpdateShownUtubeResponse.build(utuber, UUID.randomUUID())

        then:
        thrown(IllegalArgumentException)
    }
}
