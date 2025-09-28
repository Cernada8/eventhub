package com.example.eventhub.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "companies")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=200)
    private String name;

    @Column(length=32)
    private String cif;

    @Column(length=255) private String website;
    @Column(length=255) private String contactEmail;
    @Column(length=30)  private String phone;

    @Column(length=255) private String addressLine;
    @Column(length=120) private String city;
    @Column(length=20)  private String postalCode;
    @Column(length=120) private String country;

    @Column(nullable=false, updatable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;
    @PrePersist void prePersist() { createdAt = updatedAt = Instant.now(); }
    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }
}
