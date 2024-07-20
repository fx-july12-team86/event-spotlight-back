package org.example.eventspotlightback.repository;

import java.util.Optional;
import org.example.eventspotlightback.model.Favorite;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @EntityGraph(attributePaths = {"events"})
    Optional<Favorite> findByUserId(long id);
}
