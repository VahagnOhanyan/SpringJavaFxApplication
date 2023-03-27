package ohanyan.repo;


import ohanyan.domain.UserEntity;
import ohanyan.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



public interface UserService{

    Optional<UserEntity> findByUserInfoId(UserInfoEntity userInfoId);


}
