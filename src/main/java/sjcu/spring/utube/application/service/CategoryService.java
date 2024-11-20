package sjcu.spring.utube.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sjcu.spring.utube.adapter.request.CreateCategoryRequest;
import sjcu.spring.utube.application.port.in.CategoryInputPort;
import sjcu.spring.utube.application.port.out.CategoryOutputPort;
import sjcu.spring.utube.domain.entities.Category;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryInputPort {
    private final CategoryOutputPort categoryOutputPort;

    @Override
    public Category createCategory(CreateCategoryRequest request) {
        var categoryTemp = Category.builder()
            .categoryName(request.categoryName())
            .build();

        return categoryOutputPort.save(categoryTemp);
    }

    @Override
    public List<Category> findCategories() {
        return categoryOutputPort.findAll();
    }
}
