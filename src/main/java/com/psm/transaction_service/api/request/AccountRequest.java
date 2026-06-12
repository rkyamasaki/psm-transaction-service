package com.psm.transaction_service.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Account creation request")
public record AccountRequest(

        @Schema(
                description = "Client Document number",
                example = "02851715785"
        )
        @NotBlank(message = "Field documentNumber must be informed")
        @Size(max = 20)
        @Pattern(
                regexp = "-?\\d+",
                message = "Only numbers are allowed"
        )
        String documentNumber
) {

}
