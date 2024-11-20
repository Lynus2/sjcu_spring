package sjcu.spring.utube.application.port.vo;

import lombok.Builder;
import java.util.UUID;

@Builder
public record LatestUtubesVo(
    UUID utuberId,
    String utuberName,
    UUID utubeId,
    String utubeTitle,
    String utubeThumbnail,
    String utubeUrl,
    boolean isShown,
    String category
) {
}