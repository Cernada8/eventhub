package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(name="uq_roles_name", columnNames = "name")})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private RoleName name;
}
