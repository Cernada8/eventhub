package com.example.eventhub.domain;
import com.example.eventhub.domain.enums.CompanyRole;
import com.example.eventhub.domain.ids.CompanyUserId;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "company_users")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CompanyUser {
    @EmbeddedId private CompanyUserId id;

    @ManyToOne(fetch=FetchType.LAZY) @MapsId("companyId")
    @JoinColumn(name="company_id", nullable=false, foreignKey=@ForeignKey(name="fk_company_users_company"))
    private Company company;

    @ManyToOne(fetch=FetchType.LAZY) @MapsId("clientId")
    @JoinColumn(name="client_id", nullable=false, foreignKey=@ForeignKey(name="fk_company_users_client"))
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(name="role_in_company", nullable=false, length=16)
    private CompanyRole roleInCompany;

    @Column(nullable=false) private Instant addedAt;
    @PrePersist void prePersist() { if (addedAt==null) addedAt = Instant.now(); if (roleInCompany==null) roleInCompany = CompanyRole.STAFF; }
}
