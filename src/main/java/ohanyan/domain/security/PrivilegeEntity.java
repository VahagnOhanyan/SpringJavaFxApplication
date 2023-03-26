package ohanyan.domain.security;

import lombok.*;
import ohanyan.domain.ModuleEntity;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "privileges")
public class PrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    private int privilegeId;

    @Column(name = "privilege_name")
    private String privilegeName;
    @ManyToOne
    @JoinColumn(name = "access_type_id", referencedColumnName = "access_type_id")
    private AccessTypeEntity accessTypeId;

    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "module_id")
    private ModuleEntity moduleID;

    public PrivilegeEntity(String s, AccessTypeEntity accessType, ModuleEntity module) {
        privilegeName = s;
        accessTypeId = accessType;
        moduleID = module;
    }
}
