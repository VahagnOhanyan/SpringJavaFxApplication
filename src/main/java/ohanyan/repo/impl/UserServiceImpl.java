package ohanyan.repo.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.UserEntity;
import ohanyan.domain.UserInfoEntity;
import ohanyan.domain.security.UserRoleEntity;
import ohanyan.repo.UserRepository;
import ohanyan.repo.UserRoleRepository;
import ohanyan.repo.UserRoleService;
import ohanyan.repo.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> findByUserInfoId(UserInfoEntity userInfoId) {
        return userRepository.findByUserInfoId(userInfoId);
    }
}
