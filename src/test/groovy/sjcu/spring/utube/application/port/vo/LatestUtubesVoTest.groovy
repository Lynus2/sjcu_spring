package sjcu.spring.utube.application.port.vo

import spock.lang.Specification

class LatestUtubesVoTest extends Specification {
    def utuberId = UUID.randomUUID()
    def utuberName = "utuberName"
    def utubeId = UUID.randomUUID()
    def utubeTitle = "utubeTitle"
    def utubeThumbnail = "utubeThumbnail"
    def utubeUrl = "utubeUrl"
    def isShown = false
    def category = "category"

    def "LatestUtubesVo 객체 생성"() {
        when:
        def latestUtubesVo = LatestUtubesVo.builder()
                .utuberId(utuberId)
                .utuberName(utuberName)
                .utubeId(utubeId)
                .utubeTitle(utubeTitle)
                .utubeThumbnail(utubeThumbnail)
                .utubeUrl(utubeUrl)
                .isShown(isShown)
                .category(category)
                .build()

        then:
        latestUtubesVo.utuberId() == utuberId
        latestUtubesVo.utuberName() == utuberName
        latestUtubesVo.utubeId() == utubeId
        latestUtubesVo.utubeTitle() == utubeTitle
        latestUtubesVo.utubeThumbnail() == utubeThumbnail
        latestUtubesVo.utubeUrl() == utubeUrl
        latestUtubesVo.isShown() == isShown
        latestUtubesVo.category() == category
    }
}
