package ohanyan.repo;


import ohanyan.domain.AccessSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessSectionRepository extends JpaRepository<AccessSectionEntity, Integer> {


    Optional<AccessSectionEntity> findByAccessSectionName(String accessSectionName);
}