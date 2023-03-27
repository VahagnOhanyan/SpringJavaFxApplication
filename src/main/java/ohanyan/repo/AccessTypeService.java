package ohanyan.repo;

import ohanyan.domain.AccessSectionEntity;
import ohanyan.domain.security.AccessTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessTypeService{
    List<AccessTypeEntity> findAll();
}