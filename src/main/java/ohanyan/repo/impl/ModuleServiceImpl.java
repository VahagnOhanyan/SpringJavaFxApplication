package ohanyan.repo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.AccessSectionEntity;
import ohanyan.domain.ModuleEntity;
import ohanyan.repo.ModuleRepository;
import ohanyan.repo.ModuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public List<ModuleEntity> findByAccessSectionId(AccessSectionEntity accessSectionId) {
        return moduleRepository.findByAccessSectionId(accessSectionId);
    }
}
