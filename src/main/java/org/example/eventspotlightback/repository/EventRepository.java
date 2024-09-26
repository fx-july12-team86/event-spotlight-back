package org.example.eventspotlightback.repository;

import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends
        JpaRepository<Event, Long>,
        JpaSpecificationExecutor<Event> {
    @EntityGraph(attributePaths = {"description", "address", "photos", "categories"})
    List<Event> findAll();

    @EntityGraph(attributePaths = {
            "description",
            "contact",
            "address",
            "photos",
            "categories"
    })
    Page<Event> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {
            "description",
            "contact",
            "address",
            "photos",
            "categories"
    })
    Page<Event> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = {
            "description",
            "contact",
            "address",
            "address.city",
            "photos",
            "categories",
            "favorites",
            "myEvents"
    })
    Optional<Event> findById(Long id);

    @EntityGraph(attributePaths = {
            "myEvents",
            "photos",
            "categories",
            "address",
            "address.city",
            "contact",
            "user"
    }, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Event> findEventWithMyEventsById(Long eventId);

    @EntityGraph(attributePaths = {
            "favorites",
            "photos",
            "categories",
            "address",
            "address.city",
            "contact",
            "user"
    }, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Event> findEventWithFavoriteById(Long eventId);
}
