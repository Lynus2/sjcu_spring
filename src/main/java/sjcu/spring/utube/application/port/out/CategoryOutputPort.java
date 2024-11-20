package sjcu.spring.utube.application.port.out;

import sjcu.spring.utube.domain.entities.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryOutputPort {
    Optional<Category> findByCategoryId(UUID categoryId);
    Category save(Category category);
    List<Category> findAll();
}
