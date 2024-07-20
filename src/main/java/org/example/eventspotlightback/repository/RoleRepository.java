package org.example.eventspotlightback.repository;

import org.example.eventspotlightback.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByRoleName(Role.RoleName roleName);
}
