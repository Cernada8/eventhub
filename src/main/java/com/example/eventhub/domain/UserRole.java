package com.example.eventhub.domain;
import com.example.eventhub.domain.ids.UserRoleId;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "user_roles")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("clientId")
    @JoinColumn(name="client_id", nullable=false, foreignKey=@ForeignKey(name="fk_user_roles_client"))
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("roleId")
    @JoinColumn(name="role_id", nullable=false, foreignKey=@ForeignKey(name="fk_user_roles_role"))
    private Role role;

    @Column(nullable=false)
    private Instant assignedAt;

    @PrePersist void prePersist() { if (assignedAt==null) assignedAt = Instant.now(); }
}
