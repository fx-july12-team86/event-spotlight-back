package org.example.eventspotlightback.repository.specification.event;

import org.example.eventspotlightback.exception.IllegalArgumentException;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Event> {
    private static final String KEY = "title";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        if (params.length != 1) {
            throw new IllegalArgumentException("title requires a single parameter");
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(KEY)),
                        "%" + params[0].toLowerCase() + "%");
    }
}
