package com.milosz000.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
}
