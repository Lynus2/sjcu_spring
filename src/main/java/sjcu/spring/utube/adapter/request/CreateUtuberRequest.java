package sjcu.spring.utube.adapter.request;

import java.util.UUID;

public record CreateUtuberRequest(
    String utuberName,
    String utuberUrl,
    UUID categoryId
) {
}
