package sjcu.spring.utube.adapter.response;

import lombok.Builder;
import sjcu.spring.utube.domain.entities.Utuber;

import java.util.UUID;

@Builder
public record ToggleUtuberIsUseResponse(
    UUID utuberId,
    boolean isUse
) {
    public static ToggleUtuberIsUseResponse build(Utuber utuber) {
        return ToggleUtuberIsUseResponse.builder()
            .utuberId(utuber.utuberId())
            .isUse(utuber.isUse())
            .build();
    }
}
