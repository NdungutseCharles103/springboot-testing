package rw.ac.rca.gradesclassb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.gradesclassb.enumerations.EUserRole;
import rw.ac.rca.gradesclassb.models.Role;
import rw.ac.rca.gradesclassb.models.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(EUserRole roleName);

    boolean existsByRoleName(EUserRole name);
}
