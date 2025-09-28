package com.example.eventhub.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name="promo_code_redemptions",
       uniqueConstraints = {@UniqueConstraint(name="uq_redemption_per_order", columnNames = {"discount_id","order_id"})})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PromoCodeRedemption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="discount_id", nullable=false, foreignKey=@ForeignKey(name="fk_redemptions_discount"))
    private Discount discount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="client_id", nullable=false, foreignKey=@ForeignKey(name="fk_redemptions_client"))
    private Client client;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", foreignKey=@ForeignKey(name="fk_redemptions_order"))
    private Order order;

    @Column(nullable=false) private Instant usedAt;

    @PrePersist void prePersist() { if (usedAt==null) usedAt = Instant.now(); }
}
