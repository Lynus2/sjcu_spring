package sjcu.spring.utube.adapter.thymeleaf

import org.springframework.ui.Model
import sjcu.spring.utube.application.port.in.UtuberInputPort
import sjcu.spring.utube.application.port.vo.LatestUtubesVo
import sjcu.spring.utube.domain.entities.Category
import sjcu.spring.utube.domain.entities.Utuber
import spock.lang.Specification

class UtuberThymeleafAdapterTest extends Specification {
    def utuberInputPort = Mock(UtuberInputPort)
    def utuberThymeleafAdapter = new UtuberThymeleafAdapter(utuberInputPort)
    def model = Mock(Model)

    def utuber1 = Utuber.builder()
        .utuberName("utuber1")
        .utuberUrl("utuberUrl")
        .category(Category.builder()
            .categoryName("category1")
            .build())
        .build()
    def utuber2 = Utuber.builder()
        .utuberName("utuber2")
        .utuberUrl("utuberUrl")
        .category(Category.builder()
            .categoryName("category1")
            .build())
        .build()
    def utubers = [utuber1, utuber2]

    def "유튜버 리스트 조회시 utuber.html을 반환한다."() {
        when:
        def result = utuberThymeleafAdapter.viewUtubers(model)

        then:
        noExceptionThrown()
        1 * utuberInputPort.findUtubers() >> utubers
        result == "utuber"
    }

    def "최근 유튜브 리스트 조회시 utube.html을 반환한다."() {
        when:
        def result = utuberThymeleafAdapter.viewLatestUtubes(model)

        then:
        noExceptionThrown()
        1 * utuberInputPort.findLatestUtubes() >> [
                LatestUtubesVo.builder()
                        .utuberId(UUID.randomUUID())
                        .utuberName("utuberName")
                        .utubeId(UUID.randomUUID())
                        .utubeTitle("utubeTitle")
                        .utubeThumbnail("utubeThumbnail")
                        .utubeUrl("utubeUrl")
                        .isShown(false)
                        .category("category")
                        .build()]
        result == "utube"
    }
}
