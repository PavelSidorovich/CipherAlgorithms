package com.sidorovich.tatarinov.cpl.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CorruptedMessageException extends RuntimeException {

    private final Integer fieldLength;

}
