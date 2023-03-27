package ohanyan.repo.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.security.UserRoleEntity;
import ohanyan.repo.UserRoleRepository;
import ohanyan.repo.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public Optional<UserRoleEntity> findByUserRoleName(String userRoleName) {
        return userRoleRepository.findByUserRoleNameIgnoreCase(userRoleName);
    }

    @Override
    public List<UserRoleEntity> findAll() {
        return userRoleRepository.findAll();
    }

}
