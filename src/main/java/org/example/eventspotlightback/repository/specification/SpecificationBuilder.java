package org.example.eventspotlightback.repository.specification;

import org.example.eventspotlightback.dto.internal.event.EventSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(EventSearchParameters eventSearchParameters);
}
