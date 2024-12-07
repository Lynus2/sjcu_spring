package sjcu.spring.utube.adapter.rest

import sjcu.spring.utube.adapter.request.CreateUtuberRequest
import sjcu.spring.utube.adapter.request.UpdateUtuberRequest
import sjcu.spring.utube.adapter.response.FindUtubersResponse
import sjcu.spring.utube.application.port.in.UtuberInputPort
import sjcu.spring.utube.application.port.vo.LatestUtubesVo
import sjcu.spring.utube.domain.entities.Category
import sjcu.spring.utube.domain.entities.Utube
import sjcu.spring.utube.domain.entities.Utuber
import spock.lang.Specification

class UtuberRestAdapterTest extends Specification {
    def utuberInputPort = Mock(UtuberInputPort)
    def utuberRestAdapter = new UtuberRestAdapter(utuberInputPort)

    def utube = new Utube(
            UUID.randomUUID(),
    "utubeTitle1",
    "utubeThumbnail1",
    "utubeUrl1",
    false,
    null)
    def category = Category.builder()
            .categoryName("category1")
            .build()
    def utuber = Utuber.builder()
            .utuberName("utuberName")
            .utuberUrl("utuberUrl")
            .category(category)
            .build()

    def setup() {
        def utubes = List.of(utube)
        utuber.utubes = utubes
    }

    def "유튜버 리스트 조회"() {
        given:
        utuberInputPort.findUtubers() >> List.of(utuber)

        when:
        def result = utuberRestAdapter.findUtubers()

        then:
        noExceptionThrown()
        result.body.getFirst().utuberId() == utuber.utuberId()
    }

    def "유튜버 등록"() {
        given:
        def request = CreateUtuberRequest.builder()
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .categoryId(UUID.randomUUID())
                .build()
        utuberInputPort.createUtuber(_) >> utuber

        when:
        def result = utuberRestAdapter.createUtuber(request)

        then:
        noExceptionThrown()
        result.body.utuberId() == utuber.utuberId()
    }

    def "유튜버 사용여부 활성화"() {
        given:
        utuberInputPort.toggleUtuberUse(_) >> utuber

        when:
        def result = utuberRestAdapter.toggleUtuberUse(utuber.utuberId())

        then:
        noExceptionThrown()
        result.body.utuberId() == utuber.utuberId()
    }

    def "유튜버 사용여부 비활성화"() {
        given:
        utuberInputPort.toggleUtuberDisUse(_) >> utuber

        when:
        def result = utuberRestAdapter.toggleUtuberDisUse(utuber.utuberId())

        then:
        noExceptionThrown()
        result.body.utuberId() == utuber.utuberId()
    }

    def "모든 유튜버의 최근 영상 하나씩 조회"() {
        given:
        def latestUtubesVo = LatestUtubesVo.builder()
                .utuberId(UUID.randomUUID())
                .utuberName("utuberName")
                .utubeId(UUID.randomUUID())
                .utubeTitle("utubeTitle")
                .utubeThumbnail("utubeThumbnail")
                .utubeUrl("utubeUrl")
                .isShown(false)
                .category("category")
                .build()
        utuberInputPort.findLatestUtubes() >> List.of(latestUtubesVo)

        when:
        def result = utuberRestAdapter.findLatest()

        then:
        noExceptionThrown()
        result.body.getFirst().list().getFirst().utuberId() == latestUtubesVo.utuberId()
    }

    def "시청 이력 기록"() {
        given:
        utuberInputPort.updateShownUtube(_) >> utuber

        when:
        def result = utuberRestAdapter.updateShownUtube(utuber.utuberId(), utube.utubeId())

        then:
        noExceptionThrown()
        result.body.utuberId() == utuber.utuberId()
        result.body.utubeId() == utube.utubeId()
    }

    def "utuber 수정"() {
        given:
        def request = UpdateUtuberRequest.builder()
                .categoryId(category.categoryId())
                .utuberName("utuberName")
                .utuberUrl("utuberUrl")
                .build()

        utuberInputPort.updateUtuber(_, _) >> utuber

        when:
        def result = utuberRestAdapter.updateUtuber(utuber.utuberId(), request)

        then:
        noExceptionThrown()
        result.body.utuberId() == utuber.utuberId()
    }

    def "utuber 삭제"() {
        given:
        utuberInputPort.deleteUtuber(_) >> utuber

        when:
        def result = utuberRestAdapter.updateUtuber(utuber.utuberId())

        then:
        noExceptionThrown()
        result.body.utuberId() == utuber.utuberId()
    }
}
