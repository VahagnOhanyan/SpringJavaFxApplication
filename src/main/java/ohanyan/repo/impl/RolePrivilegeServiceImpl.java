package ohanyan.repo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.security.RolePrivilegeEntity;
import ohanyan.domain.security.UserRoleEntity;
import ohanyan.repo.RolePrivilegeRepository;
import ohanyan.repo.RolePrivilegeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    public List<RolePrivilegeEntity> findByUserRoleId(UserRoleEntity userRoleId) {
        return rolePrivilegeRepository.findByUserRoleId(userRoleId);
    }

    @Override
    public void save(RolePrivilegeEntity rolePrivilegeEntity) {
        rolePrivilegeRepository.save(rolePrivilegeEntity);
    }

    @Override
    public void deleteAll(List<RolePrivilegeEntity> rolePrivileges) {
        rolePrivilegeRepository.deleteAll();
    }
}
