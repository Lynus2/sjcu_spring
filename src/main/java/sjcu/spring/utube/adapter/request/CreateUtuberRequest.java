package sjcu.spring.utube.adapter.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUtuberRequest(
    String utuberName,
    String utuberUrl,
    UUID categoryId
) {
}
