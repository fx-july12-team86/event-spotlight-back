package org.example.eventspotlightback.repository;

import java.util.Set;
import org.example.eventspotlightback.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findAllByIdIn(Set<Long> ids);
}
