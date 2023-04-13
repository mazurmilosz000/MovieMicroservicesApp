package com.milosz000.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDto {
    String token;
    String refreshToken;
}
