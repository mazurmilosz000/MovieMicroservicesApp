package com.milosz000.dto;

import com.milosz000.validation.ValidConfirmedField;
import com.milosz000.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: create validators exception handler
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidConfirmedField(originalField = "password", confirmationField = "passwordConfirmation")
public class RegisterRequestDto {

    @NotBlank(message = "Invalid username: blank username")
    @Size(min = 3, max = 30, message = "Invalid name: must be of 3-30 characters")
    private String username;

    @NotBlank(message = "Invalid first name: blank first name")
    private String firstname;

    @NotBlank(message = "Invalid last name: blank last name")
    private String lastname;

    @NotBlank(message = "Invalid email: blank email")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Invalid password: blank password")
    @ValidPassword
    private String password;

    @NotBlank(message = "Invalid name: blank password confirmation")
    private String passwordConfirmation;

}
