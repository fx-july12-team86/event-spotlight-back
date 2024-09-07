package org.example.eventspotlightback.repository;

import java.util.Optional;
import org.example.eventspotlightback.model.MyEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEventsRepository extends JpaRepository<MyEvents, Long> {
    @Query("SELECT m FROM MyEvents m "
            + "LEFT JOIN FETCH m.events e "
            + "LEFT JOIN FETCH m.user u "
            + "LEFT JOIN FETCH e.address ad "
            + "LEFT JOIN FETCH ad.city ci "
            + "LEFT JOIN FETCH e.photos p "
            + "LEFT JOIN FETCH e.categories c "
            + "WHERE m.id = :myEventId")
    Optional<MyEvents> findMyEventsById(@Param("myEventId") Long myEventId);
}
