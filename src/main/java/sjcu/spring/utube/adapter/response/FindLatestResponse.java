package sjcu.spring.utube.adapter.response;

import lombok.Builder;
import sjcu.spring.utube.application.port.vo.LatestUtubesVo;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record FindLatestResponse(
    String category,
    List<LatestResponse> list
) {
    @Builder
    private record LatestResponse(
        UUID utuberId,
        String utuberName,
        UUID utubeId,
        String utubeTitle,
        String utubeThumbnail,
        String utubeUrl,
        boolean isShown
    ) {
    }

    public static List<FindLatestResponse> builds(List<LatestUtubesVo> list) {
        // 그룹화 로직: 각 utube 객체에서 category에 따라 그룹을 나누어 FindLatestResponse를 생성
        Map<String, List<LatestResponse>> groupedByCategory = list.stream()
            .collect(Collectors.groupingBy(
                LatestUtubesVo::category,  // 각 utube의 카테고리 기준으로 그룹화
                Collectors.mapping(utube -> LatestResponse.builder()
                    .utuberId(utube.utuberId())
                    .utuberName(utube.utuberName())
                    .utubeId(utube.utubeId())
                    .utubeUrl(utube.utubeUrl())
                    .utubeThumbnail(utube.utubeThumbnail())
                    .utubeTitle(utube.utubeTitle())
                    .isShown(utube.isShown())
                    .build(),
                Collectors.toList()
                )
            ));

        // 각 카테고리 그룹을 FindLatestResponse로 변환
        return groupedByCategory.entrySet().stream()
            .map(entry -> FindLatestResponse.builder()
                .category(entry.getKey())
                .list(entry.getValue())
                .build())
            .collect(Collectors.toList());
    }
}
