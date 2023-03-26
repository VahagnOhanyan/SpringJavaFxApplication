package ohanyan.repo;

import ohanyan.domain.security.AccessTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTypeRepository extends JpaRepository<AccessTypeEntity, Integer> {
}