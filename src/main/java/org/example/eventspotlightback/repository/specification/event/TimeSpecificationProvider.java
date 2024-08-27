package org.example.eventspotlightback.repository.specification.event;

import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;

public class TimeSpecificationProvider implements SpecificationProvider<Event> {
    private static final String KEY = "time";

    @Override
    public String getKey() {
        return " ";
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        return null;
    }
}
