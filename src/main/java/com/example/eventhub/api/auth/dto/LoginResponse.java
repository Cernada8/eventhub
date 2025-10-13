package com.example.eventhub.api.auth.dto;
import jakarta.validation.constraints.*;

public record LoginResponse(
       @NotBlank
        String status,

        @NotBlank
        String message
) {


}
