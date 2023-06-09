package ohanyan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * todo Document type CsvEntity
 */
@Getter
@Setter
@Entity
@Table(name = "csv", schema = "public", catalog = "myDb")
@IdClass(CsvEntityPK.class)
public class CsvEntity {

    @Id
    @Column(name = "num")
    private String num;
    @Basic
    @Column(name = "fullname")
    private String fullname;
    @Id
    @Column(name = "monthyear")
    private String monthyear;
    @Basic
    @Column(name = "worked")
    private Double worked;
    @Basic
    @Column(name = "hospital")
    private Double hospital;
    @Basic
    @Column(name = "vacation")
    private Double vacation;
    @Basic
    @Column(name = "total")
    private double total;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CsvEntity that = (CsvEntity) o;
        return Double.compare(that.total, total) == 0 && Objects.equals(num, that.num) && Objects.equals(fullname, that.fullname) &&
                Objects.equals(monthyear, that.monthyear) && Objects.equals(worked, that.worked) &&
                Objects.equals(hospital, that.hospital) && Objects.equals(vacation, that.vacation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, fullname, monthyear, worked, hospital, vacation, total);
    }
}
