package org.example.eventspotlightback.repository;

import java.util.Optional;
import org.example.eventspotlightback.model.Favorite;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @EntityGraph(attributePaths = {"events"})
    @Query("SELECT f FROM Favorite f "
            + "LEFT JOIN FETCH f.events e "
            + "LEFT JOIN FETCH f.user u "
            + "LEFT JOIN FETCH e.address ad "
            + "LEFT JOIN FETCH ad.city ci "
            + "LEFT JOIN FETCH e.photos p "
            + "LEFT JOIN FETCH e.categories c "
            + "WHERE f.id = :favoriteId")
    Optional<Favorite> findByUserId(@Param("favoriteId") Long favoriteId);
}
