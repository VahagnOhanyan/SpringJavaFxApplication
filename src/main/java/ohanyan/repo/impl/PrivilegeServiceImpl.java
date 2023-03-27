package ohanyan.repo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.security.AccessTypeEntity;
import ohanyan.domain.security.PrivilegeEntity;
import ohanyan.repo.PrivilegeRepository;
import ohanyan.repo.PrivilegeService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;

    @Override
    public Optional<PrivilegeEntity> findByPrivilegeNameAndAccessTypeId(String privilegeName, AccessTypeEntity accessTypeId) {
        return privilegeRepository.findByPrivilegeNameAndAccessTypeId(privilegeName, accessTypeId);
    }

    @Override
    public void save(PrivilegeEntity privilegeEntity) {
        privilegeRepository.save(privilegeEntity);
    }
}
