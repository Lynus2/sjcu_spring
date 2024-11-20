package sjcu.spring.utube.adapter.response;

import lombok.Builder;
import sjcu.spring.utube.domain.entities.Utuber;

import java.util.UUID;

@Builder
public record UpdateShownUtubeResponse(
    UUID utuberId,
    UUID utubeId,
    boolean isShown
) {
    public static UpdateShownUtubeResponse build(Utuber utuber, UUID utubeId) {
        return utuber.utubes()
            .stream()
            .filter(utube -> utube.utubeId().equals(utubeId))
            .findFirst()
            .map(utube -> UpdateShownUtubeResponse.builder()
                .utuberId(utuber.utuberId())
                .utubeId(utubeId)
                .isShown(utube.isShown())
                .build())
            .orElseThrow(() -> new IllegalArgumentException("Utube with ID " + utubeId + " not found"));
    }
}
