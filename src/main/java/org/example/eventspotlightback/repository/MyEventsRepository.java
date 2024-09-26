package org.example.eventspotlightback.repository;

import java.util.Optional;
import org.example.eventspotlightback.model.MyEvents;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEventsRepository extends JpaRepository<MyEvents, Long> {
    @EntityGraph(attributePaths = {
            "events",
            "user",
            "events.address",
            "events.address.city",
            "events.photos",
            "events.categories",
    }, type = EntityGraph.EntityGraphType.FETCH)
    Optional<MyEvents> findMyEventsById(Long myEventId);
}
