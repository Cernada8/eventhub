package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name="orders", uniqueConstraints = {@UniqueConstraint(name="uq_orders_reference", columnNames="reference")})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=24) private String reference;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="client_id", nullable=false, foreignKey=@ForeignKey(name="fk_orders_client"))
    private Client client;

    @Column(nullable=false, precision=12, scale=2) private BigDecimal totalAmount;
    @Column(nullable=false, length=3) private String currency;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16)
    private OrderStatus status;

    @Column(length=50) private String paymentProvider;

    @Column(nullable=false, updatable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;
    @PrePersist void prePersist() { createdAt = updatedAt = Instant.now(); if (status==null) status = OrderStatus.PENDING; if (currency==null) currency="EUR"; if (totalAmount==null) totalAmount = BigDecimal.ZERO; }
    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }
}
