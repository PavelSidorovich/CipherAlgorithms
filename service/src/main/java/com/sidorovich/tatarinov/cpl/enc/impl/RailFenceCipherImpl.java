package com.sidorovich.tatarinov.cpl.enc.impl;

import com.sidorovich.tatarinov.cpl.enc.RailFenceCipher;
import com.sidorovich.tatarinov.cpl.model.EncryptionModel;
import org.springframework.stereotype.Service;

@Service
public class RailFenceCipherImpl implements RailFenceCipher {

    private static final char NULL_CHAR = '\u0000';

    @Override
    public EncryptionModel<Integer> encrypt(EncryptionModel<Integer> input) {
        final String message = input.getMessage();
        final Integer key = input.getKey();
        final int messageLength = message.length();
        final char[][] output = new char[key][messageLength];

        fillWithNulls(output, messageLength, key);
        fillMessageChars(output, message, key);

        return new EncryptionModel<>(getEncryptedMessage(output, messageLength, key));
    }

    private void fillMessageChars(char[][] output, String message, Integer key) {
        final int messageLength = message.length();
        boolean moveDown = false;
        int row = 0;
        int col = 0;

        for (int i = 0; i < messageLength; i++) {
            if (row == 0 || row == key - 1) {
                moveDown = !moveDown;
            }
            output[row][col++] = message.charAt(i);
            row = getDirection(moveDown, row);
        }
    }

    private String getEncryptedMessage(char[][] output, int messageLength, Integer key) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < messageLength; j++) {
                if (output[i][j] != NULL_CHAR) {
                    sb.append(output[i][j]);
                }
            }
        }
        return sb.toString();
    }

    @Override
    public EncryptionModel<Integer> decrypt(EncryptionModel<Integer> input) {
        final String message = input.getMessage();
        final Integer key = input.getKey();
        final int messageLength = message.length();
        final char[][] output = new char[key][messageLength];

        fillWithNulls(output, messageLength, key);
        fillWithAsterisks(output, messageLength, key);
        fillWithCharsOnAsteriskPositions(output, key, message);

        return new EncryptionModel<>(getDecipheredMessage(output, messageLength, key));
    }

    private String getDecipheredMessage(char[][] output, int messageLength, Integer key) {
        final StringBuilder sb = new StringBuilder();
        boolean moveDown = false;
        int row = 0;
        int col = 0;

        for (int i = 0; i < messageLength; i++) {
            if (row == 0) {
                moveDown = true;
            }
            if (row == key - 1) {
                moveDown = false;
            }
            if (output[row][col] != '*') {
                sb.append(output[row][col++]);
            }
            row = getDirection(moveDown, row);
        }
        return sb.toString();
    }

    private void fillWithCharsOnAsteriskPositions(char[][] output, Integer key, String message) {
        final int messageLength = message.length();
        int index = 0;

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < messageLength; j++) {
                if (output[i][j] == '*' && index < messageLength) {
                    output[i][j] = message.charAt(index++);
                }
            }
        }
    }

    private void fillWithAsterisks(char[][] output, int messageLength, Integer key) {
        boolean moveDown = false;
        int row = 0;
        int col = 0;

        for (int i = 0; i < messageLength; i++) {
            if (row == 0) {
                moveDown = true;
            }
            if (row == key - 1) {
                moveDown = false;
            }
            output[row][col++] = '*';
            row = getDirection(moveDown, row);
        }
    }

    private void fillWithNulls(char[][] output, int messageLength, Integer key) {
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < messageLength; j++) {
                output[i][j] = NULL_CHAR;
            }
        }
    }

    private int getDirection(boolean moveDown, int row) {
        if (moveDown) {
            row++;
        } else {
            row--;
        }
        return row;
    }

}
