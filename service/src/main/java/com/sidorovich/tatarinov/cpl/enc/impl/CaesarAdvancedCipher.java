package com.sidorovich.tatarinov.cpl.enc.impl;

import com.sidorovich.tatarinov.cpl.enc.CaesarCipher;
import com.sidorovich.tatarinov.cpl.model.EncryptionModel;
import org.springframework.stereotype.Service;

@Service("advancedCaesar")
public class CaesarAdvancedCipher implements CaesarCipher {

    private static final int ENGLISH_SYMBOLS_AMOUNT = 26;
    private static final int ASCII_SYMBOLS_INDENT = 65;

    @Override
    public EncryptionModel<Integer> encrypt(EncryptionModel<Integer> input) {
        final String message = input.getMessage();
        final Integer key = input.getKey();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            sb.append((char) (((message.charAt(i) - ASCII_SYMBOLS_INDENT) * key)
                              % ENGLISH_SYMBOLS_AMOUNT + ASCII_SYMBOLS_INDENT));
        }

        return new EncryptionModel<>(sb.toString());
    }

    @Override
    public EncryptionModel<Integer> decrypt(EncryptionModel<Integer> input) {
        return encrypt(input);
    }

}
