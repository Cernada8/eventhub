package com.example.eventhub.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.math.BigDecimal;

@Entity
@Table(name = "venues")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Venue {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=200)
    private String name;

    @Column(length=255) private String addressLine;
    @Column(length=120) private String city;
    @Column(length=20) private String postalCode;
    @Column(length=120) private String country;

    @Column(precision=9, scale=6) private BigDecimal geoLat;
    @Column(precision=9, scale=6) private BigDecimal geoLng;

    private Integer capacity;

    @Column(nullable=false, updatable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;
    @PrePersist void prePersist() { createdAt = updatedAt = Instant.now(); }
    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }
}
