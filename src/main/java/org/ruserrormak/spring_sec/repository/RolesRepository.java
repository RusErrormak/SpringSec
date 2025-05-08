package org.ruserrormak.spring_sec.repository;

import org.ruserrormak.spring_sec.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}
