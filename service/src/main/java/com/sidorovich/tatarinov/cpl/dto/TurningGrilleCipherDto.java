package com.sidorovich.tatarinov.cpl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class TurningGrilleCipherDto {

    @NotBlank(message = "{model.field.null}")
    @Pattern(regexp = "^[^#]+$", message = "{grill.symbol.not.supported}")
    private String message;

    @NotBlank(message = "{model.field.null}")
    private String key;

}