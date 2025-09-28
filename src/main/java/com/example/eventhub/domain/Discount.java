package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name="discounts", uniqueConstraints = {@UniqueConstraint(name="uq_discounts_code", columnNames="code")})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Discount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=50) private String code;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=10)
    private DiscountType type;

    @Column(nullable=false, precision=12, scale=2) private BigDecimal value;

    private Instant validFrom;
    private Instant validUntil;
    private Integer maxRedemptions;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=12)
    private DiscountScope appliesTo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="event_id", foreignKey=@ForeignKey(name="fk_discounts_event"))
    private Event event;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticket_type_id", foreignKey=@ForeignKey(name="fk_discounts_tickettype"))
    private TicketType ticketType;

    @Column(nullable=false) private boolean active;
    @Column(nullable=false, updatable=false) private Instant createdAt;

    @PrePersist void prePersist() { createdAt = Instant.now(); if (appliesTo==null) appliesTo = DiscountScope.GLOBAL; }
}
