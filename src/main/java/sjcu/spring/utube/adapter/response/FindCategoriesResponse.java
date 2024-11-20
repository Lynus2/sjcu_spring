package sjcu.spring.utube.adapter.response;

import lombok.Builder;
import sjcu.spring.utube.domain.entities.Category;

import java.util.List;
import java.util.UUID;

@Builder
public record FindCategoriesResponse(
    UUID categoryId,
    String categoryName
) {
    public static List<FindCategoriesResponse> build(List<Category> list) {
        return list.stream()
            .map(category -> FindCategoriesResponse.builder()
                .categoryId(category.categoryId())
                .categoryName(category.categoryName())
                .build())
            .toList();
    }
}
