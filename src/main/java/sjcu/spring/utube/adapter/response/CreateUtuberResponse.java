package sjcu.spring.utube.adapter.response;

import lombok.Builder;
import sjcu.spring.utube.domain.entities.Utuber;

import java.util.UUID;

@Builder
public record CreateUtuberResponse(
    UUID utuberId,
    String categoryName,
    String utuberName,
    String utuberUrl,
    boolean isUse
) {
    public static CreateUtuberResponse build(Utuber utuber) {
        return CreateUtuberResponse.builder()
            .utuberId(utuber.utuberId())
            .utuberUrl(utuber.utuberUrl())
            .categoryName(utuber.category().categoryName())
            .utuberName(utuber.utuberName())
            .isUse(utuber.isUse())
            .build();
    }
}
