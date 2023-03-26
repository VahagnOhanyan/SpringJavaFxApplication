package ohanyan.repo;


import ohanyan.domain.security.AccessTypeEntity;
import ohanyan.domain.security.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Integer> {
    Optional<PrivilegeEntity> findByPrivilegeNameAndAccessTypeId(String privilegeName, AccessTypeEntity accessTypeId);

}