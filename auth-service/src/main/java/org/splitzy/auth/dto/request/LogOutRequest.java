package org.splitzy.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogOutRequest {
    @NotBlank(message = "Access token is required")
    private String acessToken;
    private String refreshToken;
}