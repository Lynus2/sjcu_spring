package sjcu.spring.utube.domain.entities

import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

import java.time.LocalDateTime

class UtuberTest extends Specification {
    def category1 = Category.builder()
            .categoryName("category1")
            .build()
    def category2 = Category.builder()
            .categoryName("category1")
            .build()
    def utuberName = "utuber"
    def utuberUrl = "utuberUrl"
    def newUtube = new Utube(UUID.randomUUID(),
            "utubeTitle",
            "utubeThumbnail",
            "utubeUrl",
            true,
            LocalDateTime.of(2024, 10, 10, 10, 10, 10))
    def oldUtube = new Utube(
            UUID.randomUUID(),
            "utubeTitle2",
            "utubeThumbnail2",
            "utubeUrl2",
            false,
            LocalDateTime.of(2024, 10, 9, 10, 10, 10))
    def utubes = [newUtube, oldUtube]

    def utuber1 = Utuber.builder()
            .utuberName(utuberName)
            .utuberUrl(utuberUrl)
            .category(category1)
            .build()
    def utuber2 = Utuber.builder()
            .utuberName(utuberName)
            .utuberUrl(utuberUrl)
            .category(category1)
            .build()

    def "Utuber 객체 생성"() {
        when:
        def utuber = Utuber.builder()
                .utuberName(utuberName)
                .utuberUrl(utuberUrl)
                .category(category1)
                .build()

        then:
        noExceptionThrown()
        utuber.utuberName() == utuberName
        utuber.utuberUrl() == utuberUrl
        utuber.category() == category1
    }

    def "Utuber 객체 생성시 기본값 조회"() {
        when:
        def utuber = Utuber.builder()
                .utuberName(utuberName)
                .utuberUrl(utuberUrl)
                .category(category1)
                .build()

        then:
        noExceptionThrown()
        !utuber.isUse()
    }

    def "Utuber 사용 처리"() {
        when:
        utuber1.toggleUtuberUse()

        then:
        noExceptionThrown()
        utuber1.isUse()
    }

    def "Utuber 미사용 처리"() {
        given:
        utuber1.toggleUtuberUse()

        when:
        utuber1.toggleUtuberDisUse()

        then:
        noExceptionThrown()
        !utuber1.isUse()
    }

    def "Utuber 수정"() {
        given:
        def utuberName2 = "utuberName2"
        def utuberUrl2 = "utuberUrl2"

        when:
        utuber1.updateUtuber(utuberName2, utuberUrl2, category2)

        then:
        noExceptionThrown()
        utuber1.utuberName() == utuberName2
        utuber1.utuberUrl() == utuberUrl2
        utuber1.category() == category2
    }

    def "Utuber의 utube 리스트 조회"() {
        given:
        utuber1.utubes = utubes

        when:
        def utuberUtubes = utuber1.utubes()

        then:
        noExceptionThrown()
        utuberUtubes == utubes
    }



    def "Utuber의 가장 최근 utube 조회"() {
        given:
        ReflectionTestUtils.setField(newUtube, "createdAt", LocalDateTime.now().minusDays(1));
        ReflectionTestUtils.setField(oldUtube, "createdAt", LocalDateTime.now().minusDays(2));
        ReflectionTestUtils.setField(utuber1, "utubes", utubes);

        when:
        def latestUtube = utuber1.latestUtube()

        then:
        noExceptionThrown()
        latestUtube == newUtube
    }

    def "시청한 utube의 시청 이력을 기록한다"() {
        given:
        ReflectionTestUtils.setField(newUtube, "createdAt", LocalDateTime.now().minusDays(1));
        ReflectionTestUtils.setField(oldUtube, "createdAt", LocalDateTime.now().minusDays(2));
        ReflectionTestUtils.setField(utuber1, "utubes", utubes);

        when:
        utuber1.updateShownUtube(newUtube.utubeId())
        utuber1.updateShownUtube(oldUtube.utubeId())

        then:
        utuber1.utubes().forEach(utube -> utube.isShown())
    }
}
