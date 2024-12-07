package sjcu.spring.utube.domain

import spock.lang.Specification

import java.time.LocalDateTime

class AuditEntityTest extends Specification {
    def auditEntity = new AuditEntity()
    def "createdAt should return the creation date"() {
        given:
        def now = LocalDateTime.now()
        auditEntity.createdAt = now

        when:
        def result = auditEntity.createdAt()

        then:
        result == now
    }

    def "delete should set deletedAt to current time"() {
        when:
        auditEntity.delete()

        then:
        auditEntity.deletedAt() != null
        auditEntity.deletedAt().isBefore(LocalDateTime.now()) || auditEntity.deletedAt().isEqual(LocalDateTime.now())
    }

    def "deletedAt should return null when not deleted"() {
        expect:
        auditEntity.deletedAt() == null
    }

    def "deletedAt should return deletion time after delete"() {
        when:
        auditEntity.delete()
        def deletionTime = auditEntity.deletedAt()

        then:
        deletionTime != null
        deletionTime.isBefore(LocalDateTime.now()) || deletionTime.isEqual(LocalDateTime.now())
    }
}
