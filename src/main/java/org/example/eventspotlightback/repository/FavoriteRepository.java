package org.example.eventspotlightback.repository;

import java.util.Optional;
import org.example.eventspotlightback.model.Favorite;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @EntityGraph(attributePaths = {
            "events",
            "user",
            "events.address",
            "events.address.city",
            "events.photos",
            "events.categories",
    }, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Favorite> findByUserId(Long favoriteId);
}
