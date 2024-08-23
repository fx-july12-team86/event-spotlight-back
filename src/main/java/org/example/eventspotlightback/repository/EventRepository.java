package org.example.eventspotlightback.repository;

import java.util.List;
import java.util.Optional;
import org.example.eventspotlightback.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends
        JpaRepository<Event, Long>,
        JpaSpecificationExecutor<Event> {
    @EntityGraph(attributePaths = {"description", "address", "photos", "categories"})
    List<Event> findAll();

    @EntityGraph(attributePaths = {"description", "address", "photos", "categories"})
    Page<Event> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"description", "address", "photos", "categories"})
    Optional<Event> findById(Long id);
}
