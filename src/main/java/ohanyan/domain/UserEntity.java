package ohanyan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "user", schema = "public", catalog = "myDb")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "user_surname")
    private String userSurname;
    @Basic
    @Column(name = "user_name")
    private String userName;
    @Basic
    @Column(name = "user_midname")
    private String userMidname;
    @Basic
    @Column(name = "user_fullname")
    private String userFullname;
    @Basic
    @Column(name = "user_tel")
    private String userTel;
    @Basic
    @Column(name = "user_address")
    private String userAddress;
    @Basic
    @Column(name = "user_email")
    private String userEmail;
    @ManyToOne
    @JoinColumn(name = "user_info_id", referencedColumnName = "user_info_id")
    private UserInfoEntity userInfoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return userId == that.userId && Objects.equals(userSurname, that.userSurname) &&
                Objects.equals(userName, that.userName) && Objects.equals(userMidname, that.userMidname) &&
                Objects.equals(userFullname, that.userFullname) && Objects.equals(userTel, that.userTel) &&
                Objects.equals(userAddress, that.userAddress) && Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(userInfoId, that.userInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userSurname, userName, userMidname, userFullname, userTel, userAddress, userEmail, userInfoId);
    }
}
