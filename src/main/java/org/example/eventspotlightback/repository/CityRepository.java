package org.example.eventspotlightback.repository;

import org.example.eventspotlightback.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
