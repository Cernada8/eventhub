package com.example.eventhub.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name="webhook_events", uniqueConstraints = {@UniqueConstraint(name="uq_webhook_ext", columnNames = {"provider","external_id"})})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WebhookEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=50) private String provider;
    @Column(name="external_id", nullable=false, length=100) private String externalId;
    @Column(name="event_type", nullable=false, length=100) private String eventType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition="json", nullable=false)
    private String payload;

    @Column(nullable=false) private Instant receivedAt;
    private Instant processedAt;

    @PrePersist void prePersist() { if (receivedAt==null) receivedAt = Instant.now(); }
}
