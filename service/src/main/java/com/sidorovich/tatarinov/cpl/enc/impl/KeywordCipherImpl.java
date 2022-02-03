package com.sidorovich.tatarinov.cpl.enc.impl;

import com.sidorovich.tatarinov.cpl.enc.KeywordCipher;
import com.sidorovich.tatarinov.cpl.model.EncryptionModel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

@Service
public class KeywordCipherImpl implements KeywordCipher {

    @Override
    public EncryptionModel<String> encrypt(EncryptionModel<String> input) {
        final String message = input.getMessage();
        final String key = input.getKey();
        final int messageLength = message.length();
        final int keyLength = key.length();
        Map<Integer, Integer> charsASCIIByIndex = getCharsASCII(key);
        StringBuilder sb = new StringBuilder();
        int messageIndex = 0;

        for (int i = 0; i < messageLength / keyLength + messageLength % keyLength; i++) {
            Map<Integer, Character> charByPosition = new TreeMap<>();
            for (Entry<Integer, Integer> asciiByIndex : charsASCIIByIndex.entrySet()) {
                if (messageIndex == messageLength) {
                    break;
                }
                charByPosition.put(asciiByIndex.getValue(), message.charAt(messageIndex++));
            }
            charByPosition.forEach((keyVal, value) -> sb.append(value));
        }
        return new EncryptionModel<>(sb.toString());
    }

    @Override
    public EncryptionModel<String> decrypt(EncryptionModel<String> input) {
        final String message = input.getMessage();
        final String key = input.getKey();
        final int messageLength = message.length();
        final int keyLength = key.length();
        char[] output = new char[messageLength];
        Map<Integer, Integer> indexesByAscii = getIndexes(key);
        int messageIndex = 0;
        int step = 0;

        for (int i = 0; i < messageLength / keyLength + messageLength % keyLength; i++) {
            for (Entry<Integer, Integer> indexByAscii : indexesByAscii.entrySet()) {
                if (messageIndex > messageLength - 1) {
                    break;
                }
                if (indexByAscii.getValue() + step >= messageLength) {
                    continue;
                }
                output[indexByAscii.getValue() + step] = message.charAt(messageIndex++);
            }
            step += keyLength;
        }
        return new EncryptionModel<>(new String(output));
    }

    private Map<Integer, Integer> getCharsASCII(String keyword) {
        final Map<Integer, Integer> asciiByIndex = new TreeMap<>();
        final int messageLength = keyword.length();
        int bias = 0;

        for (int i = 0; i < messageLength; i++) {
            int position = keyword.charAt(i);
            int asciiWithBias = asciiByIndex.containsValue(position + bias)
                    ? position + ++bias : position + bias;
            asciiByIndex.put(i, asciiWithBias);
        }
        return asciiByIndex;
    }

    private Map<Integer, Integer> getIndexes(String message) {
        final Map<Integer, Integer> indexesByAscii = new TreeMap<>();

        getCharsASCII(message).forEach((key, value) -> indexesByAscii.put(value, key));
        return indexesByAscii;
    }

}
