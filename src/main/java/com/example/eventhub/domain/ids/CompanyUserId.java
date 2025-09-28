package com.example.eventhub.domain.ids;
import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUserId implements Serializable {
    private Long companyId;
    private Long clientId;
}
