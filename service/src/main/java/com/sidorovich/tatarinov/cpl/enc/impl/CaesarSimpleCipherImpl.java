package com.sidorovich.tatarinov.cpl.enc.impl;

import com.sidorovich.tatarinov.cpl.enc.CaesarCipher;
import com.sidorovich.tatarinov.cpl.model.EncryptionModel;
import org.springframework.stereotype.Service;

@Service("simpleCaesar")
public class CaesarSimpleCipherImpl implements CaesarCipher {

    private static final int ASCII_SYMBOLS_AMOUNT = 256;

    @Override
    public EncryptionModel<Integer> encrypt(EncryptionModel<Integer> input) {
        final String message = input.getMessage();
        final Integer key = input.getKey();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            sb.append((char) ((message.charAt(i) + key) % ASCII_SYMBOLS_AMOUNT));
        }

        return new EncryptionModel<>(sb.toString());
    }

    @Override
    public EncryptionModel<Integer> decrypt(EncryptionModel<Integer> input) {
        final String message = input.getMessage();
        final Integer key = input.getKey();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            sb.append((char) ((message.charAt(i) - key) % ASCII_SYMBOLS_AMOUNT));
        }

        return new EncryptionModel<>(sb.toString());
    }

}
