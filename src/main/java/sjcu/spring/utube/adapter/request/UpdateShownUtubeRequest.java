package sjcu.spring.utube.adapter.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateShownUtubeRequest(
    UUID utuberId,
    UUID utubeId
) {
}