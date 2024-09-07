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
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT e FROM Event e "
            + "LEFT JOIN FETCH e.myEvents m "
            + "LEFT JOIN FETCH e.photos p "
            + "LEFT JOIN FETCH e.categories c "
            + "LEFT JOIN FETCH e.address a "
            + "LEFT JOIN FETCH e.contact co "
            + "LEFT JOIN FETCH e.user u "
            + "WHERE e.id = :eventId")
    Optional<Event> findByIdWithMyEvents(@Param("eventId")Long eventId);

    @Query("SELECT e FROM Event e "
            + "LEFT JOIN FETCH e.favorites f "
            + "LEFT JOIN FETCH e.photos p "
            + "LEFT JOIN FETCH e.categories c "
            + "LEFT JOIN FETCH e.address a "
            + "LEFT JOIN FETCH e.contact co "
            + "LEFT JOIN FETCH e.user u "
            + "WHERE e.id = :eventId")
    Optional<Event> findByIdWithFavorite(@Param("eventId")Long eventId);
}
