package sjcu.spring.utube.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sjcu.spring.utube.application.port.out.CategoryOutputPort;
import sjcu.spring.utube.domain.entities.Category;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, CategoryOutputPort {
}
