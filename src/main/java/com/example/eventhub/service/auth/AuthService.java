package com.example.eventhub.service.auth;

import com.example.eventhub.api.auth.dto.LoginRequest;
import com.example.eventhub.api.auth.dto.LoginResponse;
import com.example.eventhub.api.auth.dto.RegisterRequest;
import com.example.eventhub.api.auth.dto.RegisterResponse;
import com.example.eventhub.domain.Client;
import com.example.eventhub.domain.enums.ClientStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- usa ESTA
import org.springframework.web.servlet.function.EntityResponse;

import java.time.Instant;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public RegisterResponse register(RegisterRequest req) {
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
        // em.flush(); // sólo si quieres forzar errores de constraint ahora
    }

    
    @Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest req) {

        // Consulta para buscar el cliente en la base de datos
        Client client = em.createQuery(
                "SELECT c FROM Client c WHERE c.email = :email", Client.class)
                .setParameter("email", req.email())
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (client.getEmail() == req.email() && passwordEncoder.matches(req.password(), client.getPasswordHash())) {
            return new LoginResponse("error", "Credenciales inválidas.");
        } else {
            return new LoginResponse("ok", "Inicio de sesión correcto.");
        }
    }
}
