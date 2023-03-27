package ohanyan.repo;


import ohanyan.domain.UserInfoEntity;
import ohanyan.domain.security.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



public interface UserInfoService {
    @Transactional
    @Modifying
    @Query("update UserInfoEntity u set u.userRoleId = ?1 where u.userLogin = ?2")
    int updateUserRole(UserRoleEntity userRoleId, String userLogin);

    Optional<UserInfoEntity> findByUserLoginAndUserPass(String userLogin, String userPass);

    Optional<UserInfoEntity> findByUserLogin(String userLogin);


}
