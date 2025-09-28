package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.CheckinResult;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name="checkins")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Checkin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticket_id", nullable=false, foreignKey=@ForeignKey(name="fk_checkins_ticket"))
    private Ticket ticket;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="scanner_user_id", foreignKey=@ForeignKey(name="fk_checkins_scanner"))
    private Client scannerUser;

    @Column(nullable=false) private Instant scannedAt;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=12)
    private CheckinResult result;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private String deviceInfo;

    @PrePersist void prePersist() { if (scannedAt==null) scannedAt = Instant.now(); if (result==null) result = CheckinResult.OK; }
}
