package ohanyan.repo;

import ohanyan.domain.AccessSectionEntity;
import ohanyan.domain.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {
    List<ModuleEntity> findByAccessSectionId(AccessSectionEntity accessSectionId);

}
