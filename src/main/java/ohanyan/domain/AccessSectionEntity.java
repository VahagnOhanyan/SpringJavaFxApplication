package ohanyan.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "access_section", schema = "public", catalog = "myDb")
public class AccessSectionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "access_section_id")
    private int accessSectionId;
    @Column(name = "access_section_name")
    private String accessSectionName;

}
