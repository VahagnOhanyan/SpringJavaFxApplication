package ohanyan.repo;

import ohanyan.domain.security.RolePrivilegeEntity;
import ohanyan.domain.security.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilegeEntity, Integer> {
    List<RolePrivilegeEntity> findByUserRoleId(UserRoleEntity userRoleId);

}