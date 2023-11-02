package ru.ssau.webcaffe.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data public class LoginRequest {
    @NotBlank(message = "The username must be filled in")
    private String username;
    @NotBlank(message = "The password must be filled in")
    private String password;
}
