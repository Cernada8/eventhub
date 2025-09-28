package com.example.eventhub.service.auth;

import com.example.eventhub.api.auth.dto.RegisterRequest;
import com.example.eventhub.api.auth.dto.RegisterResponse;
import com.example.eventhub.domain.Client;
import com.example.eventhub.domain.enums.ClientStatus;
import com.example.eventhub.repo.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- usa ESTA
import org.springframework.web.servlet.function.EntityResponse;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepo;

    @PersistenceContext
    private EntityManager em;

    //TODO --> TRADUCTOR PARA TODAS LAS RESPUESTAS
    @Transactional
    public RegisterResponse register(RegisterRequest req) {
        if (userExists(req.email())) {
            return new RegisterResponse("error", "El email ya existe.");
        }

        insertInfo(req);
        return new RegisterResponse("ok", "El usuario ha sido registrado en el sistema.");
    }

    private void insertInfo(RegisterRequest req) {
        Client newClient = new Client();
        newClient.setBirthDate(req.birthDate());
        newClient.setEmail(req.email());
        newClient.setName(req.name());
        newClient.setPasswordHash(passwordEncoder.encode(req.password()));
        newClient.setCreatedAt(Instant.now());
        newClient.setUpdatedAt(Instant.now());
        newClient.setStatus(ClientStatus.ACTIVE);
        newClient.setPhone(req.phone());

        em.persist(newClient);
    }

    private boolean userExists(String email) {
        Client example = new Client();
        example.setEmail(email);
        Example<Client> clientExample = Example.of(example);

        return clientRepo.exists(clientExample);
    }
}
