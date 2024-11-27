package sjcu.spring.utube.application.port.in.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUtuberCommand(
    String utuberName,
    String utuberUrl,
    UUID categoryId
) {
}
