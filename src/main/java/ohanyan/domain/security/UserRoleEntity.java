package ohanyan.domain.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "user_role", schema = "public", catalog = "myDb")
public class UserRoleEntity implements Comparable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_role_id")
    private int userRoleId;
    @Basic
    @Column(name = "user_role_name")
    private String userRoleName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_privileges",
            joinColumns = @JoinColumn(name = "user_role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<PrivilegeEntity> privileges;
    @Column(name = "sort")
    private int sort;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRoleEntity that = (UserRoleEntity) o;
        return userRoleId == that.userRoleId && Objects.equals(userRoleName, that.userRoleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleId, userRoleName);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof UserRoleEntity) {
            if (getSort() > ((UserRoleEntity) o).getSort()) {
                return 1;
            } else if (getSort() < ((UserRoleEntity) o).getSort()) {
                return -1;
            }

        }
        return 0;

    }
}
