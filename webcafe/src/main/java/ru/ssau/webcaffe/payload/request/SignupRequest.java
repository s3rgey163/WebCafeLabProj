package ru.ssau.webcaffe.payload.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import ru.ssau.webcaffe.entity.User;

import java.time.LocalDateTime;

@Builder
@Value public class SignupRequest {


    @NotBlank(message = "The username must be filled in")
    @Size(min = 5)
    String username;

    @Email(message = "Should have email format")
    @NotBlank(message = "The email must be filled in")
    String email;

    @NotBlank(message = "The firstname must be filled in")
    String firstname;

    @NotBlank(message = "The middlename must be filled in")
    String middlename;

    @NotBlank(message = "The secondname must be filled in")
    String secondname;

    @NotNull(message = "The birthday must be filled in")
    LocalDateTime birthday;

    @Size(min = 8)
    @NotBlank(message = "The password must be filled in")
    String password;

    @Size(min = 8)
    @NotBlank(message = "The confirm password must be filled in")
    String confirmPassword;

    @NotNull(message = "The gender must be filled in")
    User.Gender gender;
}
