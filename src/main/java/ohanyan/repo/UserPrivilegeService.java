package ohanyan.repo;


import ohanyan.domain.UserEntity;
import ohanyan.domain.UserInfoEntity;
import ohanyan.domain.security.UserPrivilegeEntity;

import java.util.List;
import java.util.Optional;


public interface UserPrivilegeService {

    List<UserPrivilegeEntity> findByUserInfoId(UserInfoEntity userInfoId);


    void deleteAll(List<UserPrivilegeEntity> userPrivileges);

    void save(UserPrivilegeEntity userPrivilegeEntity);

}
