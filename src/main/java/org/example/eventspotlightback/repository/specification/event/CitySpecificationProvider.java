package org.example.eventspotlightback.repository.specification.event;

import jakarta.persistence.criteria.Join;
import java.util.Arrays;
import org.example.eventspotlightback.model.Address;
import org.example.eventspotlightback.model.City;
import org.example.eventspotlightback.model.Event;
import org.example.eventspotlightback.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CitySpecificationProvider implements SpecificationProvider<Event> {
    private static final String KEY = "cities";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Event, Address> addressJoin = root.join("address");
            Join<Address, City> cityJoin = addressJoin.join("city");
            return criteriaBuilder.and(cityJoin.get("id").in(Arrays.stream(params).toArray()));
        };
    }
}
