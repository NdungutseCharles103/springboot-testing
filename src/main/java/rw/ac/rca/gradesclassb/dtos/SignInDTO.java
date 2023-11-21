package rw.ac.rca.gradesclassb.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {

    @NotNull (message = "Email is required")
    @Email (message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    private String password;
}


