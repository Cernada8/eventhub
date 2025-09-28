package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "clients", indexes = {@Index(name="idx_clients_email", columnList = "email")}, uniqueConstraints = {@UniqueConstraint(name="uq_clients_email", columnNames = "email")})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(name="password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(length = 30)
    private String phone;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ClientStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist void prePersist() { createdAt = updatedAt = Instant.now(); if (status == null) status = ClientStatus.ACTIVE; }
    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }
}
