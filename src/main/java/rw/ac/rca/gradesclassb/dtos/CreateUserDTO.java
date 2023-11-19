package rw.ac.rca.gradesclassb.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import rw.ac.rca.gradesclassb.enumerations.EGender;
import rw.ac.rca.gradesclassb.enumerations.EUserRole;
import rw.ac.rca.gradesclassb.security.ValidPassword;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateUserDTO {
    @NotNull
    @Length(min = 2)
    private String firstName;

    @NotNull
    @Length(min = 2)
    private String lastName;

    @NotNull
    private EGender gender;

    @Email
    private String emailAddress;

    @NotNull
    @Pattern(regexp = "\\+250\\d{9}")
    private String phoneNumber;


    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    private EUserRole roleName;

}
