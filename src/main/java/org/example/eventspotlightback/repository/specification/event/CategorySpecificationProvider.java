package org.example.eventspotlightback.repository.specification.event;

import java.util.Arrays;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.eventspotlightback.model.Category;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecificationProvider implements SpecificationProvider<Event> {
    private static final String KEY = "categories";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Event, Category> categoryJoin = root.join(KEY);
            return criteriaBuilder.and(
                    categoryJoin.get("id").in(Arrays.stream(params).toArray()));
        };
    }
}
