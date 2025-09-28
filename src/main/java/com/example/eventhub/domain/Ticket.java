package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name="tickets", uniqueConstraints = {
        @UniqueConstraint(name="uq_tickets_serial", columnNames="serial"),
        @UniqueConstraint(name="uq_tickets_qr", columnNames="qr_payload")
})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_item_id", nullable=false, foreignKey=@ForeignKey(name="fk_tickets_order_item"))
    private OrderItem orderItem;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticket_type_id", nullable=false, foreignKey=@ForeignKey(name="fk_tickets_ticket_type"))
    private TicketType ticketType;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="event_id", nullable=false, foreignKey=@ForeignKey(name="fk_tickets_event"))
    private Event event;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="client_id", foreignKey=@ForeignKey(name="fk_tickets_client"))
    private Client client;

    @Column(nullable=false, length=36) private String serial;
    @Column(name="qr_payload", nullable=false, length=128) private String qrPayload;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16)
    private TicketStatus status;

    private Instant issuedAt;
}
