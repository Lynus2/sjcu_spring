package sjcu.spring.utube.adapter.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateUtuberRequest(
    UUID categoryId,
    String utuberName,
    String utuberUrl
) {
}