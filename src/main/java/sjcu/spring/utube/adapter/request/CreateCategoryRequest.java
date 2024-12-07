package sjcu.spring.utube.adapter.request;

import lombok.Builder;

@Builder
public record CreateCategoryRequest(
    String categoryName
) {
}
