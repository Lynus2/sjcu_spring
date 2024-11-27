package sjcu.spring.utube.adapter.thymeleaf

import org.springframework.ui.Model
import sjcu.spring.utube.application.port.in.UtuberInputPort
import sjcu.spring.utube.application.port.vo.LatestUtubesVo
import spock.lang.Specification

class CategoryThymeleafAdapterTest extends Specification {
    def utuberInputPort = Mock(UtuberInputPort)
    def categoryThymeleafAdapter = new CategoryThymeleafAdapter(utuberInputPort)
    def model = Mock(Model)

    def "Category를 조회한다...? 확인 필요"() {
        when:
        def result = categoryThymeleafAdapter.viewCategory(model)

        then:
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
                        .build()
        ]
    }
}
