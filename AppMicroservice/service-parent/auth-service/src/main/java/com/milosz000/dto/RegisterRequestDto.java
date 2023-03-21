package com.milosz000.dto;

import com.milosz000.validation.ValidConfirmedField;
import com.milosz000.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidConfirmedField(originalField = "password", confirmationField = "passwordConfirmation")
public class RegisterRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

    @NotBlank
    private String passwordConfirmation;

}
