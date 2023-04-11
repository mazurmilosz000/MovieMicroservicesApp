package com.milosz000.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {

    @NotBlank(message = "Invalid username: blank username")
    private String username;

    @NotBlank(message = "Invalid password: blank password")
    private String password;

}
