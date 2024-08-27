package org.example.eventspotlightback.repository.specification.event;

import org.example.eventspotlightback.exception.IllegalArgumentException;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OnlineStatusSpecificationProvider implements SpecificationProvider<Event> {
    public static final String KEY = "isOnline";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        if (params.length != 1) {
            throw new IllegalArgumentException("isOnline requires a single parameter");
        }

        Boolean isOnline = Boolean.parseBoolean(params[0]);

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(KEY), isOnline);
    }
}
