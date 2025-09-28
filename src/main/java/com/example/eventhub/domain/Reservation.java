package com.example.eventhub.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name="reservations", indexes = {@Index(name="idx_reservations_expiry", columnList="expiresAt")})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="client_id", nullable=false, foreignKey=@ForeignKey(name="fk_reservations_client"))
    private Client client;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticket_type_id", nullable=false, foreignKey=@ForeignKey(name="fk_reservations_tickettype"))
    private TicketType ticketType;

    @Column(nullable=false) private Integer quantity;
    @Column(nullable=false) private Instant expiresAt;
    @Column(nullable=false, updatable=false) private Instant createdAt;

    @PrePersist void prePersist() { createdAt = Instant.now(); }
}
