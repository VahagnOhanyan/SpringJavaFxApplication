package ohanyan.repo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.AccessSectionEntity;
import ohanyan.repo.AccessSectionRepository;
import ohanyan.repo.AccessSectionService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccessSectionServiceImpl implements AccessSectionService {
    private final AccessSectionRepository accessSectionRepository;

    @Override
    public Optional<AccessSectionEntity> findByAccessSectionName(String accessSectionName) {
        return accessSectionRepository.findByAccessSectionName(accessSectionName);
    }
}
