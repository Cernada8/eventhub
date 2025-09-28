package com.example.eventhub.api.auth.dto;
import jakarta.validation.constraints.*;

public record RegisterResponse(
       @NotBlank
        String status,

        @NotBlank
        String message
) {


}
