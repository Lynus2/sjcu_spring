package sjcu.spring.utube.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEnity {

    @CreatedDate
    @Column(name="created_at", updatable = false)
    LocalDateTime createdAt;

    @CreatedBy
    @Column(name="created_by", updatable = false)
    UUID createdBy;

    @LastModifiedDate
    @Column(name="modified_at")
    LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(name="modified_by")
    UUID modifiedBy;

    LocalDateTime deletedAt;

    UUID deletedBy;

    public LocalDateTime createdAt() {
        return this.createdAt;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
