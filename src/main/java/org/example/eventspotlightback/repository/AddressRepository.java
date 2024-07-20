package org.example.eventspotlightback.repository;

import org.example.eventspotlightback.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
