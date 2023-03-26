package ohanyan.domain.security;

import lombok.Getter;
import lombok.Setter;
import ohanyan.domain.UserInfoEntity;
import ohanyan.domain.security.PrivilegeEntity;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "user_privileges", schema = "public", catalog = "myDb")
public class UserPrivilegeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_privilege_id")
    private int userPrivilegeId;
    @ManyToOne
    @JoinColumn(name = "user_info_id", referencedColumnName = "user_info_id")
    private UserInfoEntity userInfoId;
    @OneToOne
    @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id")
    private PrivilegeEntity privilegeId;


    public UserPrivilegeEntity(PrivilegeEntity privilege, UserInfoEntity user) {
        privilegeId = privilege;
        userInfoId = user;
    }

    public UserPrivilegeEntity() {

    }
}
