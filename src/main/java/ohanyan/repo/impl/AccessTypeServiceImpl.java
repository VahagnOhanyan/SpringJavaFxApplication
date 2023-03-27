package ohanyan.repo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.AccessSectionEntity;
import ohanyan.domain.security.AccessTypeEntity;
import ohanyan.repo.AccessSectionRepository;
import ohanyan.repo.AccessTypeRepository;
import ohanyan.repo.AccessTypeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccessTypeServiceImpl implements AccessTypeService {
    private final AccessTypeRepository accessTypeRepository;

    @Override
    public List<AccessTypeEntity> findAll() {
        return accessTypeRepository.findAll();
    }
}
