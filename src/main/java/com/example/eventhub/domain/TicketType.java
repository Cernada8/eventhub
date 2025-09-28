package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.TicketTypeStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "ticket_types", uniqueConstraints = {@UniqueConstraint(name="uq_ticket_types_event_name", columnNames = {"event_id","name"})})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TicketType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="event_id", nullable=false, foreignKey=@ForeignKey(name="fk_ticket_types_event"))
    private Event event;

    @Column(nullable=false, length=120) private String name;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal price;
    @Column(nullable=false, length=3) private String currency;
    @Column(nullable=false) private Integer capacity;

    private Instant salesStartsAt;
    private Instant salesEndsAt;
    private Integer maxPerOrder;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16)
    private TicketTypeStatus status;

    @Column(nullable=false, updatable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;
    @PrePersist void prePersist() { createdAt = updatedAt = Instant.now(); if (status==null) status = TicketTypeStatus.ACTIVE; if (currency==null) currency="EUR"; }
    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }
}
