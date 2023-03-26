package ohanyan.repo;


import ohanyan.domain.security.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
    Optional<UserRoleEntity> findByUserRoleNameIgnoreCase(String userRoleName);

}
