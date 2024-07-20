package org.example.eventspotlightback.repository;

import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.model.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    @EntityGraph(attributePaths = {"description", "address", "photos", "categories"})
    List<Event> findAll();

    @EntityGraph(attributePaths = {"description", "address", "photos", "categories"})
    Optional<Event> findById(Long id);
}
