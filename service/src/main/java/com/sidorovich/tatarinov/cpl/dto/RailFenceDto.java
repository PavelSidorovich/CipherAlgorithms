package com.sidorovich.tatarinov.cpl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RailFenceDto {

    @NotBlank(message = "{model.field.null}")
    private String message;

    @NotNull(message = "{model.field.null}")
    @Min(value = 2, message = "{model.field.number.min}")
    private Integer key;

}

