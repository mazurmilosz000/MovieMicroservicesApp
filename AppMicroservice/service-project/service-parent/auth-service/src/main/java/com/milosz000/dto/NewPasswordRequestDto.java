package com.milosz000.dto;


import com.milosz000.validation.ValidConfirmedField;
import com.milosz000.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidConfirmedField(originalField = "password", confirmationField = "passwordConfirmation")
public class NewPasswordRequestDto {
    @NotBlank
    @ValidPassword
    private String password;

    @NotBlank
    private String passwordConfirmation;
}
