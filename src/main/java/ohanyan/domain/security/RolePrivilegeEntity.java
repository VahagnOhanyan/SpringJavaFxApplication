package ohanyan.domain.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "role_privileges", schema = "public", catalog = "myDb")
public class RolePrivilegeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "role_privilege_id")
    private int rolePrivilegeId;
    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id")
    private UserRoleEntity userRoleId;
    @OneToOne
    @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id")
    private PrivilegeEntity privilegeId;



    public RolePrivilegeEntity(PrivilegeEntity p, UserRoleEntity userRoleEntity) {
        privilegeId = p;
        userRoleId = userRoleEntity;
    }

    public RolePrivilegeEntity() {

    }
}
