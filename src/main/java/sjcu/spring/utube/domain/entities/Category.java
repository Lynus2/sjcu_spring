package sjcu.spring.utube.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @PrePersist
    protected void generateUUID() {
        if (categoryId == null) categoryId = UUID.randomUUID();
    }

    @Builder
    public Category(String categoryName) {
        this.categoryId = UUID.randomUUID();
        this.categoryName = categoryName;
    }

    public UUID categoryId() {
        return this.categoryId;
    }

    public String categoryName() {
        return this.categoryName;
    }
}
