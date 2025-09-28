package com.example.eventhub.domain.ids;
import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCompanyId implements Serializable {
    private Long eventId;
    private Long companyId;
}
