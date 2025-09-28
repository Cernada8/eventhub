package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "events", uniqueConstraints = {@UniqueConstraint(name="uq_events_slug", columnNames = "slug")})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=140) private String slug;
    @Column(nullable=false, length=200) private String name;
    @Lob private String description;

    @Column(nullable=false) private Instant startsAt;
    private Instant endsAt;

    @Column(nullable=false, length=50) private String timezone;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16) private EventStatus status;
    @Column(nullable=false, length=3) private String currency;
    @Column(length=255) private String coverImageUrl;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="venue_id", foreignKey=@ForeignKey(name="fk_events_venue"))
    private Venue venue;

    @Column(nullable=false, updatable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;
    @PrePersist void prePersist() { createdAt = updatedAt = Instant.now(); if (status==null) status = EventStatus.DRAFT; if (timezone==null) timezone = "Europe/Madrid"; if (currency==null) currency="EUR"; }
    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }
}
