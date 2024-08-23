package org.example.eventspotlightback.repository;

import java.util.Optional;
import org.example.eventspotlightback.model.MyEvents;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEventsRepository extends JpaRepository<MyEvents, Long> {
    @EntityGraph(attributePaths = {"events"})
    Optional<MyEvents> findMyEventsByUserId(Long id);
}
