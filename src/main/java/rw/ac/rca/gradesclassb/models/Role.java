package rw.ac.rca.gradesclassb.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import rw.ac.rca.gradesclassb.dtos.CreateRoleDTO;
import rw.ac.rca.gradesclassb.enumerations.EUserRole;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    @Column (name = "role_id")
    private UUID id;

    @Column(name="description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private EUserRole roleName;

    public Role(CreateRoleDTO dto) {
        this.roleName = dto.getName();
        this.description = dto.getDescription();
    }

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
