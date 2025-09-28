package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.EventCompanyRole;
import com.example.eventhub.domain.ids.EventCompanyId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "event_companies")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EventCompany {
    @EmbeddedId private EventCompanyId id;

    @ManyToOne(fetch=FetchType.LAZY) @MapsId("eventId")
    @JoinColumn(name="event_id", nullable=false, foreignKey=@ForeignKey(name="fk_event_companies_event"))
    private Event event;

    @ManyToOne(fetch=FetchType.LAZY) @MapsId("companyId")
    @JoinColumn(name="company_id", nullable=false, foreignKey=@ForeignKey(name="fk_event_companies_company"))
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name="role_in_event", nullable=false, length=16)
    private EventCompanyRole roleInEvent;
}
