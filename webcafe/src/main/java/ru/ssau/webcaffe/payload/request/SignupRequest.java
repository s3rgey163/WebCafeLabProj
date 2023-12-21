package ru.ssau.webcaffe.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import ru.ssau.webcaffe.annotation.validation.PasswordMatch;
import ru.ssau.webcaffe.entity.User;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@PasswordMatch(message = "Passwords doesn't match")
@Value public class SignupRequest {
    public static final String EMAIL_MATCH_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

//    @NotBlank(message = "The username must be filled in")
    @Size(min = 5)
    String username;

    @Email(message = "Should have email format", regexp = EMAIL_MATCH_PATTERN)
    @NotBlank(message = "The email must be filled in")
    String email;

    @NotBlank(message = "The firstname must be filled in")
    String firstname;

    @NotBlank(message = "The middlename must be filled in")
    String middlename;

    @NotBlank(message = "The secondname must be filled in")
    String secondname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
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

    Set<User.AuthRole> authRole = Set.of(User.AuthRole.ROLE_USER);
}
