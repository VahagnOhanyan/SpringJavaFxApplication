package ohanyan.repo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.UserInfoEntity;
import ohanyan.domain.security.UserRoleEntity;
import ohanyan.repo.UserInfoRepository;
import ohanyan.repo.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public int updateUserRole(UserRoleEntity userRoleId, String userLogin) {
        return userInfoRepository.updateUserRole(userRoleId, userLogin);
    }

    @Override
    public Optional<UserInfoEntity> findByUserLoginAndUserPass(String userLogin, String userPass) {
        return userInfoRepository.findByUserLoginAndUserPass(userLogin, userPass);
    }

    @Override
    public Optional<UserInfoEntity> findByUserLogin(String userLogin) {
        return userInfoRepository.findByUserLogin(userLogin);
    }
}
