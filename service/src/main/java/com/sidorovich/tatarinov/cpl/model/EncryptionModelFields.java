package com.sidorovich.tatarinov.cpl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EncryptionModelFields {

    MESSAGE("message"),
    KEY("key");

    private final String fieldName;

}
