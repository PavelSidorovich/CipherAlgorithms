package com.sidorovich.tatarinov.cpl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptionModel<T> {

    private String message;
    private T key;

    public EncryptionModel(String message) {
        this.key = null;
        this.message = message;
    }

}
