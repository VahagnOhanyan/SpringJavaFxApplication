package ohanyan.repo;


import ohanyan.domain.security.PrivilegeEntity;
import ohanyan.domain.UserInfoEntity;
import ohanyan.domain.security.UserPrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilegeEntity, Integer> {
    List<UserPrivilegeEntity> findByUserInfoId(UserInfoEntity userInfoId);

    Optional<UserPrivilegeEntity> findByPrivilegeId(PrivilegeEntity privilegeId);


}