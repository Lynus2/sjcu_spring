package sjcu.spring.utube.domain.entities

import jakarta.persistence.Column
import spock.lang.Specification

import java.time.LocalDateTime

class UtubeTest extends Specification {
    def utubeId = UUID.randomUUID()
    def utubeTitle = "utubeTitle"
    def utubeThumbnail = "utubeThumbnail"
    def utubeUrl = "utubeUrl"
    def isShown = false
    def shownAt = LocalDateTime.now()

    def "Utube 객체 생성"() {
        when:
        def utube = new Utube(utubeId, utubeTitle, utubeThumbnail, utubeUrl, isShown, shownAt)

        then:
        noExceptionThrown()
        utube.utubeId() == utubeId
        utube.utubeTitle() == utubeTitle
        utube.utubeThumbnail() == utubeThumbnail
        utube.utubeUrl() == utubeUrl
        utube.isShown() == isShown
    }

    def "시청하지 않은 Utube의 시청 여부 수정"() {
        given:
        def utube = new Utube(utubeId, utubeTitle, utubeThumbnail, utubeUrl, false, shownAt)

        when:
        utube.updateShown()

        then:
        noExceptionThrown()
        utube.isShown()
    }

    def "이미 시청한 Utube의 시청 여부를 수정하여도 수정되지 않는다"() {
        given:
        def utube = new Utube(utubeId, utubeTitle, utubeThumbnail, utubeUrl, true, shownAt)

        when:
        utube.updateShown()

        then:
        noExceptionThrown()
        utube.isShown()
    }
}
