package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name="payments", uniqueConstraints = {@UniqueConstraint(name="uq_payments_provider_id", columnNames = {"provider","provider_payment_id"})})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false, foreignKey=@ForeignKey(name="fk_payments_order"))
    private Order order;

    @Column(nullable=false, length=50) private String provider;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal amount;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20)
    private PaymentStatus status;

    @Column(name="provider_payment_id", nullable=false, length=100)
    private String providerPaymentId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="raw_payload", columnDefinition = "json")
    private String rawPayload;

    @Column(nullable=false, updatable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;
    @PrePersist void prePersist() { createdAt = updatedAt = Instant.now(); }
    @PreUpdate void preUpdate() { updatedAt = Instant.now(); }
}
