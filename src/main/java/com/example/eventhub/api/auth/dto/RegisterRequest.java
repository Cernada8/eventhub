package com.example.eventhub.api.auth.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

import java.util.Date;

public record RegisterRequest(
    @NotBlank
    @Size(min = 3, max = 50)
    String name,

    @Email
    @NotBlank
    String email,

    @NotBlank
    @Size(min = 6, max = 20)
    String phone,

    @NotBlank
    @Size(min = 8, max = 72)
    String password,

    @NotNull
    @Past
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate
){}
