package com.example.eventhub.domain;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name="refunds")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Refund {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", foreignKey=@ForeignKey(name="fk_refunds_order"))
    private Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticket_id", foreignKey=@ForeignKey(name="fk_refunds_ticket"))
    private Ticket ticket;

    @Column(nullable=false, precision=12, scale=2) private BigDecimal amount;
    @Column(length=255) private String reason;
    @Column(name="provider_refund_id", length=100) private String providerRefundId;

    @Column(nullable=false, updatable=false) private Instant createdAt;
    @PrePersist void prePersist() { createdAt = Instant.now(); }
}
