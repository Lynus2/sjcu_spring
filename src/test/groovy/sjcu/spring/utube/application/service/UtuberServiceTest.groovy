package sjcu.spring.utube.application.service

import org.springframework.test.util.ReflectionTestUtils
import sjcu.spring.utube.adapter.request.UpdateShownUtubeRequest
import sjcu.spring.utube.adapter.request.UpdateUtuberRequest
import sjcu.spring.utube.application.port.in.command.CreateUtuberCommand
import sjcu.spring.utube.application.port.out.CategoryOutputPort
import sjcu.spring.utube.application.port.out.UtuberOutputPort
import sjcu.spring.utube.domain.entities.Category
import sjcu.spring.utube.domain.entities.Utube
import sjcu.spring.utube.domain.entities.Utuber
import spock.lang.Specification

import java.time.LocalDateTime

class UtuberServiceTest extends Specification {
    def utuberOutputPort = Mock(UtuberOutputPort)
    def categoryOutputPort = Mock(CategoryOutputPort)
    def utuberService = new UtuberService(utuberOutputPort, categoryOutputPort)

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

    def category = Category.builder()
            .categoryName("category")
            .build()

    def setup() {
        def utubes = new ArrayList()
        utubes.add(utube)
        utuber.utubes = utubes
    }

    def "삭제처리되지 않은 유튜버 리스트를 조회한다"() {
        given:
        utuberOutputPort.findByDeletedAtIsNull() >> [utuber]

        when:
        def result = utuberService.findUtubers()

        then:
        noExceptionThrown()
        result.getFirst().getClass() == Utuber
        result.getFirst().utuberName() == utuber.utuberName()
        result.getFirst().utuberUrl() == utuber.utuberUrl()
        result.getFirst().category().categoryName() == utuber.category().categoryName()
    }

    def "Utuber 등록"() {
        given:
        def command = CreateUtuberCommand.builder()
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .categoryId(UUID.randomUUID())
                .build()

        categoryOutputPort.findByCategoryId(_) >> Optional.of(category)

        utuberOutputPort.save(_) >> Utuber.builder()
                .utuberName(command.utuberName())
                .utuberUrl(command.utuberUrl())
                .category(category)
                .build()

        when:
        def result = utuberService.createUtuber(command)

        then:
        noExceptionThrown()
        result.utuberUrl() == command.utuberUrl()
        result.utuberName() == command.utuberName()
        result.category() == category
    }

    def "Utuber들의 최신 편을 조회한다."() {
        given:
        def utuber1 = Utuber.builder()
                .utuberName("utuberName1")
                .utuberUrl("utuberUrl1")
                .category(Category.builder().categoryName("category").build())
                .build()

        def newUtube = new Utube(
                UUID.randomUUID(),
                "utubeTitle1",
                "utubeThumbnail1",
                "utubeUrl1",
                false,
                null)
        ReflectionTestUtils.setField(newUtube, "createdAt", LocalDateTime.now().minusDays(1))

        def oldUtube = new Utube(
                UUID.randomUUID(),
                "utubeTitle2",
                "utubeThumbnail2",
                "utubeUrl2",
                true,
                LocalDateTime.now().minusDays(2))
        ReflectionTestUtils.setField(oldUtube, "createdAt", LocalDateTime.now().minusDays(3))

        utuber1.utubes = [newUtube, oldUtube]

        def utuber2 = Utuber.builder()
                .utuberName("utuberName2")
                .utuberUrl("utuberUrl2")
                .category(Category.builder().categoryName("category").build())
                .build()

        utuberOutputPort.findByDeletedAtIsNull() >> [utuber1, utuber2]

        when:
        def result = utuberService.findLatestUtubes()

        then:
        noExceptionThrown()
        result.getFirst().utubeId() == newUtube.utubeId()
    }

    def "유튜브 시청 기록을 업데이트한다."() {
        given:
        def utuber = Utuber.builder()
                .utuberName("utuberName1")
                .utuberUrl("utuberUrl1")
                .category(Category.builder().categoryName("category").build())
                .build()

        def newUtube = new Utube(
                UUID.randomUUID(),
                "utubeTitle1",
                "utubeThumbnail1",
                "utubeUrl1",
                false,
                null)

        utuber.utubes = [newUtube]

        utuberOutputPort.findByUtuberId(_) >> Optional.of(utuber)
        utuberOutputPort.save(_) >> utuber

        def request = UpdateShownUtubeRequest.builder()
                .utuberId(utuber.utuberId())
                .utubeId(newUtube.utubeId())
                .build()

        when:
        def result = utuberService.updateShownUtube(request)

        then:
        noExceptionThrown()
        result.utubes()
                .stream()
                .filter(utube -> utube.utubeId() == newUtube.utubeId())
                .findFirst().get().isShown()
    }

    def "유튜브 시청 기록을 업데이트시 해당 유튜버가 없는 경우 에러 반환"() {
        given:
        utuberOutputPort.findByUtuberId(_) >> Optional.empty()

        def request = UpdateShownUtubeRequest.builder()
                .utuberId(UUID.randomUUID())
                .utubeId(UUID.randomUUID())
                .build()

        when:
        utuberService.updateShownUtube(request)

        then:
        thrown(IllegalArgumentException)
    }

    def "유튜버 사용 여부 활성화"() {
        given:
        def utuberId = utuber.utuberId()
        utuberOutputPort.findByUtuberId(_) >> Optional.of(utuber)
        utuberOutputPort.save(_) >> null

        when:
        def result = utuberService.toggleUtuberUse(utuberId)

        then:
        noExceptionThrown()
        result.isUse()
    }

    def "유튜버 사용 여부 활성화시 유튜버가 존재하지 않으면 에러 반환"() {
        given:
        def utuberId = utuber.utuberId()
        utuberOutputPort.findByUtuberId(_) >> Optional.empty()

        when:
        def result = utuberService.toggleUtuberUse(utuberId)

        then:
        thrown(IllegalArgumentException)
    }

    def "유튜버 사용 여부 비활성화"() {
        given:
        def utuberId = utuber.utuberId()
        utuberOutputPort.findByUtuberId(_) >> Optional.of(utuber)
        utuberOutputPort.save(_) >> null

        when:
        def result = utuberService.toggleUtuberDisUse(utuberId)

        then:
        !result.isUse()
    }

    def "유튜버 사용 여부 비활성화시 유튜버가 존재하지 않으면 에러 반환"() {
        given:
        def utuberId = utuber.utuberId()
        utuberOutputPort.findByUtuberId(_) >> Optional.empty()

        when:
        def result = utuberService.toggleUtuberDisUse(utuberId)

        then:
        thrown(IllegalArgumentException)
    }

    def "유튜버 정보 수정"() {
        given:
        def request = UpdateUtuberRequest.builder()
                .categoryId(UUID.randomUUID())
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .build()
        utuberOutputPort.findByUtuberId(_) >> Optional.of(utuber)
        categoryOutputPort.findByCategoryId(_) >> Optional.of(category)
        utuberOutputPort.save(_) >> null

        when:
        def result = utuberService.updateUtuber(utuber.utuberId(), request)

        then:
        noExceptionThrown()
        result.utuberId() == utuber.utuberId()
    }

    def "유튜버 정보 수정시 유튜버가 존재하지 않으면 에러 반환"() {
        given:
        def request = UpdateUtuberRequest.builder()
                .categoryId(UUID.randomUUID())
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .build()
        utuberOutputPort.findByUtuberId(_) >> Optional.empty()

        when:
        def result = utuberService.updateUtuber(utuber.utuberId(), request)

        then:
        thrown(IllegalArgumentException)
    }

    def "유튜버 정보 수정시 카테고리가 존재하지 않으면 에러 반환"() {
        given:
        def request = UpdateUtuberRequest.builder()
                .categoryId(UUID.randomUUID())
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .build()
        utuberOutputPort.findByUtuberId(_) >> Optional.of(utuber)
        categoryOutputPort.findByCategoryId(_) >> Optional.empty()

        when:
        def result = utuberService.updateUtuber(utuber.utuberId(), request)

        then:
        thrown(IllegalArgumentException)
    }

    def "유튜버 삭제"() {
        given:
        utuberOutputPort.findByUtuberId(_) >> Optional.of(utuber)
        utuberOutputPort.save(_) >> null

        when:
        def result = utuberService.deleteUtuber(UUID.randomUUID())

        then:
        noExceptionThrown()
        result.deletedAt() != null
    }

    def "유튜버 삭제시 유튜버가 존재하지 않으면 에러 반환"() {
        given:
        utuberOutputPort.findByUtuberId(_) >> Optional.empty()

        when:
        utuberService.deleteUtuber(UUID.randomUUID())

        then:
        thrown(IllegalArgumentException)
    }
}
