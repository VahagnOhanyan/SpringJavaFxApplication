package ohanyan.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ohanyan.domain.security.PrivilegeEntity;
import ohanyan.domain.security.UserRoleEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "user_info", schema = "public", catalog = "myDb")
public class UserInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_info_id")
    private int userInfoId;
    @Basic
    @Column(name = "user_login")
    private String userLogin;
    @Basic
    @Column(name = "user_pass")
    private String userPass;
    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id")
    private UserRoleEntity userRoleId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_privileges",
            joinColumns = @JoinColumn(name = "user_info_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<PrivilegeEntity> privileges;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfoEntity that = (UserInfoEntity) o;
        return userInfoId == that.userInfoId && Objects.equals(userLogin, that.userLogin) && Objects.equals(userPass, that.userPass) &&
                Objects.equals(userRoleId, that.userRoleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInfoId, userLogin, userPass, userRoleId);
    }
}
