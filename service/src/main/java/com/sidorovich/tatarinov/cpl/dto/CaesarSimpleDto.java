package com.sidorovich.tatarinov.cpl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class CaesarSimpleDto {

    @NotBlank(message = "{model.field.null}")
    private String message;

    @NotNull(message = "{model.field.null}")
    @Positive(message = "{model.field.positive}")
    private Integer key;

}
