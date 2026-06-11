package com.psm.transaction_service.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
        @NotBlank(message = "Field documentNumber must be informed")
        @Size(max = 20)
        @Pattern(
                regexp = "-?\\d+",
                message = "Only numbers are allowed"
        )
        String documentNumber
) {

}
