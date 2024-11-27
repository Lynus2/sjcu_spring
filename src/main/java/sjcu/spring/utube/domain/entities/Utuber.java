package sjcu.spring.utube.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import sjcu.spring.utube.domain.AuditEnity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utuber")
@NoArgsConstructor
public class Utuber extends AuditEnity {
    @Id
    @Column(name = "utuber_id")
    private UUID utuberId;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_UTUBER_CATEGORY_CATEGORY_ID"))
    private Category category;

    @Column(name = "utuber_name")
    private String utuberName;

    @Column(name = "utuber_url")
    private String utuberUrl;

    @Column(name = "is_use")
    private boolean isUse;

    @ElementCollection
    @CollectionTable(
        name = "utube",
        joinColumns = @JoinColumn(
            name = "utuber_id",
            foreignKey = @ForeignKey(name = "FK_UTUBER_UTUBE_UTUBER_ID")))
    private List<Utube> utubes;

    @PrePersist
    protected void generateUUID() {
        utuberId = UUID.randomUUID();
    }

    @Builder
    public Utuber(String utuberName, String utuberUrl, Category category) {
        Assert.notNull(category, "카테고리 지정 필요");

        this.utuberName = utuberName;
        this.utuberUrl = utuberUrl;
        this.category = category;
        this.utubes = new ArrayList<>();
    }

    public UUID utuberId() {
        return this.utuberId;
    }

    public String utuberName() {
        return this.utuberName;
    }

    public String utuberUrl() {
        return this.utuberUrl;
    }

    public Category category() {
        return this.category;
    }

    public boolean isUse() {
        return this.isUse;
    }

    public List<Utube> utubes() {
        return this.utubes;
    }

    public Utube latestUtube() {
        return this.utubes
            .stream()
            .max(Comparator.comparing(Utube::createdAt))
            .orElse(null);
    }

    public void updateShownUtube(UUID utubeId) {
        this.utubes.stream()
            .filter(utube -> utube.utubeId().equals(utubeId))
            .forEach(Utube::updateShown);
    }

    public void toggleUtuberUse() {
        this.isUse = true;
    }

    public void toggleUtuberDisUse() {
        this.isUse = false;
    }

    public void updateUtuber(String utuberName, String utuberUrl, Category category) {
        this.utuberName = utuberName;
        this.utuberUrl = utuberUrl;
        this.category = category;
    }
}
