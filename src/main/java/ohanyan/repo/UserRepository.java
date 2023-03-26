package ohanyan.repo;


import ohanyan.domain.UserEntity;
import ohanyan.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUserInfoId(UserInfoEntity userInfoId);


}
