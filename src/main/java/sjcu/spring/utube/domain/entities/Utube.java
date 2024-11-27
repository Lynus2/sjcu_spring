package sjcu.spring.utube.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sjcu.spring.utube.domain.AuditEnity;

import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@Table(name = "utube")
@NoArgsConstructor
@AllArgsConstructor
public class Utube extends AuditEnity {
    @Column(name = "utube_id")
    private UUID utubeId;

    @Column(name = "utube_title")
    private String utubeTitle;

    @Column(name = "utube_thumbnail")
    private String utubeThumbnail;

    @Column(name = "utube_url", unique = true)
    private String utubeUrl;

    @Column(name = "is_shown")
    private boolean isShown;

    @Column(name = "shown_at")
    private LocalDateTime shownAt;

    public UUID utubeId() {
        return this.utubeId;
    }

    public String utubeTitle() {
        return this.utubeTitle;
    }

    public String utubeThumbnail() {
        return this.utubeThumbnail;
    }

     public String utubeUrl() {
        return this.utubeUrl;
     }

     public boolean isShown() {
        return this.isShown;
     }

     public void updateShown() {
        if (this.isShown) return;

        this.isShown = true;
        this.shownAt = LocalDateTime.now();
     }
}
