package ohanyan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "module", schema = "public", catalog = "myDb")
public class ModuleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "module_id")
    private int moduleId;
    @Basic
    @Column(name = "module_name")
    private String moduleName;

    @ManyToOne
    @JoinColumn(name = "access_section_id", referencedColumnName = "access_section_id")
    private AccessSectionEntity accessSectionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModuleEntity that = (ModuleEntity) o;
        return moduleId == that.moduleId && Objects.equals(moduleName, that.moduleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleId, moduleName);
    }
}
