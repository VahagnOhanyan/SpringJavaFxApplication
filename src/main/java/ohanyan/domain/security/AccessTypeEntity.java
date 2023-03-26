package ohanyan.domain.security;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "access_type", schema = "public", catalog = "myDb")
public class AccessTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "access_type_id")
    private int accessTypeId;
    @Basic
    @Column(name = "access_type_name")
    private String accessTypeName;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccessTypeEntity that = (AccessTypeEntity) o;
        return accessTypeId == that.accessTypeId && Objects.equals(accessTypeName, that.accessTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessTypeId, accessTypeName);
    }

}
