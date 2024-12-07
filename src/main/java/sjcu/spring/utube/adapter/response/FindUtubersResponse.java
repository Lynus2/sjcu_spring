package sjcu.spring.utube.adapter.response;

import lombok.Builder;
import sjcu.spring.utube.domain.entities.Utuber;

import java.util.List;
import java.util.UUID;

@Builder
public record FindUtubersResponse(
    UUID utuberId,
    String utuberName,
    String utuberUrl,
    UUID categoryId,
    String categoryName,
    boolean isUse
) {
    public static List<FindUtubersResponse> builds(List<Utuber> list) {
        return list.stream()
            .map(FindUtubersResponse::build)
            .toList();
    }

    public static FindUtubersResponse build(Utuber utuber) {
        return FindUtubersResponse.builder()
            .utuberId(utuber.utuberId())
            .utuberName(utuber.utuberName())
            .utuberUrl(utuber.utuberUrl())
            .categoryId(utuber.category().categoryId())
            .categoryName(utuber.category().categoryName())
            .isUse(utuber.isUse())
            .build();
    }
}
