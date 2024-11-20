package sjcu.spring.utube.application.port.in;


import sjcu.spring.utube.adapter.request.CreateCategoryRequest;
import sjcu.spring.utube.domain.entities.Category;

import java.util.List;

public interface CategoryInputPort {
    Category createCategory(CreateCategoryRequest request);
    List<Category> findCategories();
}
