package ohanyan.repo;


import ohanyan.domain.AccessSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessSectionService{


    Optional<AccessSectionEntity> findByAccessSectionName(String accessSectionName);
}