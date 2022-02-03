package com.sidorovich.tatarinov.cpl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class KeywordDto {

    @NotBlank(message = "{model.field.null}")
    private String message;

    @NotBlank(message = "{model.field.null}")
    private String key;

}