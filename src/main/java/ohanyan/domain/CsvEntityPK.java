package ohanyan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * todo Document type CsvEntityPK
 */
@Getter
@Setter
@Entity
@Table
public class CsvEntityPK implements Serializable {
    @Column(name = "num")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    @Column(name = "monthyear")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String monthyear;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CsvEntityPK that = (CsvEntityPK) o;
        return Objects.equals(num, that.num) && Objects.equals(monthyear, that.monthyear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, monthyear);
    }
}
