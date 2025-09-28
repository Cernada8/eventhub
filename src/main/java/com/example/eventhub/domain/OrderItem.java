package com.example.eventhub.domain;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name="order_items")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false, foreignKey=@ForeignKey(name="fk_order_items_order"))
    private Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticket_type_id", nullable=false, foreignKey=@ForeignKey(name="fk_order_items_ticket_type"))
    private TicketType ticketType;

    @Column(nullable=false) private Integer quantity;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal unitPrice;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal subtotal;
}
