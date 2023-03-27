package ohanyan.repo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohanyan.domain.UserInfoEntity;
import ohanyan.domain.security.UserPrivilegeEntity;
import ohanyan.repo.UserPrivilegeRepository;
import ohanyan.repo.UserPrivilegeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserPrivilegeServiceImpl implements UserPrivilegeService {
    private final UserPrivilegeRepository userPrivilegeRepository;

    @Override
    public List<UserPrivilegeEntity> findByUserInfoId(UserInfoEntity userInfoId) {
        return userPrivilegeRepository.findByUserInfoId(userInfoId);
    }

    @Override
    public void deleteAll(List<UserPrivilegeEntity> userPrivileges) {
        userPrivilegeRepository.deleteAll();
    }

    @Override
    public void save(UserPrivilegeEntity userPrivilegeEntity) {
        userPrivilegeRepository.save(userPrivilegeEntity);
    }
}
