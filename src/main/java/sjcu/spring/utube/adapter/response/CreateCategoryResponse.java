package sjcu.spring.utube.adapter.response;

import lombok.Builder;
import sjcu.spring.utube.domain.entities.Category;

import java.util.UUID;

@Builder
public record CreateCategoryResponse(
    UUID categoryId,
    String categoryName
) {
    public static CreateCategoryResponse build(Category category) {
        return CreateCategoryResponse.builder()
            .categoryName(category.categoryName())
            .categoryId(category.categoryId())
            .build();
    }
}
