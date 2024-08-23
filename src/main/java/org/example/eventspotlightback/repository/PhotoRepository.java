package org.example.eventspotlightback.repository;

import java.util.Set;
import org.example.eventspotlightback.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Set<Photo> findAllByIdIn(Set<Long> ids);
}
