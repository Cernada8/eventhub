// src/main/java/com/example/eventhub/repo/ClientRepository.java
package com.example.eventhub.repo;

import com.example.eventhub.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
