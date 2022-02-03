package com.sidorovich.tatarinov.cpl.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidGrilleKeyException extends RuntimeException {

    private final String fieldName;

}
