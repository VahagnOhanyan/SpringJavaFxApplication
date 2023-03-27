package ohanyan.repo;

import ohanyan.domain.security.RolePrivilegeEntity;
import ohanyan.domain.security.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePrivilegeService{
    List<RolePrivilegeEntity> findByUserRoleId(UserRoleEntity userRoleId);

    void save(RolePrivilegeEntity rolePrivilegeEntity);

    void deleteAll(List<RolePrivilegeEntity> rolePrivileges);

}