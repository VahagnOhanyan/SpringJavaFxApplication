package ohanyan.repo;


import ohanyan.domain.security.UserRoleEntity;

import java.util.List;
import java.util.Optional;



public interface UserRoleService {
    Optional<UserRoleEntity> findByUserRoleName(String userRoleName);

    List<UserRoleEntity> findAll();
}
