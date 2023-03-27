package ohanyan.repo;

import ohanyan.domain.AccessSectionEntity;
import ohanyan.domain.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleService {
    List<ModuleEntity> findByAccessSectionId(AccessSectionEntity accessSectionId);

}
