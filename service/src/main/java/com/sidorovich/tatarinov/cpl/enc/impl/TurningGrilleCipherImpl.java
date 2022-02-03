package com.sidorovich.tatarinov.cpl.enc.impl;

import com.sidorovich.tatarinov.cpl.enc.TurningGrilleCipher;
import com.sidorovich.tatarinov.cpl.model.EncryptionModel;
import com.sidorovich.tatarinov.cpl.model.Pair;
import com.sidorovich.tatarinov.cpl.util.IndexFinder;
import com.sidorovich.tatarinov.cpl.util.MatrixRotator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
@RequiredArgsConstructor
public class TurningGrilleCipherImpl implements TurningGrilleCipher {

    private static final int MATRIX_SIZE = 4;
    private static final int MATRIX_ELEMENT_AMOUNT = MATRIX_SIZE * MATRIX_SIZE;
    private static final char FILLER_CHAR = '#';
    private static final String FILLER_STRING = "#";
    private static final char STRING_TERMINATOR = '\0';

    private final IndexFinder indexFinder;
    private final MatrixRotator<Character> matrixRotator;

    @Override
    public EncryptionModel<Integer[][]> encrypt(EncryptionModel<Integer[][]> input) {
        final StringBuilder sb = new StringBuilder();
        Map<Integer, Pair<Integer, Integer>> indexes = indexFinder.getIndexes(input.getKey());
        List<String> messageParts = splitMessageIntoParts(input.getMessage());

        for (String messagePart : messageParts) {
            Character[][] characters = buildCharGrill(messagePart, indexes);
            for (int i = 0; i < MATRIX_SIZE; i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    sb.append(characters[i][j]);
                }
            }
        }
        return new EncryptionModel<>(sb.toString());
    }

    @Override
    public EncryptionModel<Integer[][]> decrypt(EncryptionModel<Integer[][]> input) {
        final StringBuilder sb = new StringBuilder();
        final String message = input.getMessage();
        Map<Integer, Pair<Integer, Integer>> indexes = indexFinder.getIndexes(input.getKey());
        int messageIndex = 0;

        for (int i = 0; i < getNumberOfGrills(message); i++) {
            Character[][] grill = new Character[MATRIX_SIZE][MATRIX_SIZE];
            for (int j = 0; j < MATRIX_SIZE; j++) {
                for (int k = 0; k < MATRIX_SIZE; k++) {
                    grill[j][k] = message.charAt(messageIndex++);
                }
            }
            sb.append(buildMessageFromGrill(grill, indexes));
        }
        return new EncryptionModel<>(sb.toString().replaceAll(FILLER_STRING, Strings.EMPTY));
    }

    private Character[][] buildCharGrill(String message, Map<Integer, Pair<Integer, Integer>> indexes) {
        Character[][] grill = new Character[MATRIX_SIZE][MATRIX_SIZE];
        int messagePointerPosition = 0;

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (Entry<Integer, Pair<Integer, Integer>> indexesByNumber : indexes.entrySet()) {
                Pair<Integer, Integer> indexPair = indexesByNumber.getValue();
                grill[indexPair.getObject1()][indexPair.getObject2()] = message.charAt(messagePointerPosition++);
            }
            grill = matrixRotator.rotate(grill);
        }
        return grill;
    }

    private String buildMessageFromGrill(Character[][] grill, Map<Integer, Pair<Integer, Integer>> indexes) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (Entry<Integer, Pair<Integer, Integer>> indexesByNumber : indexes.entrySet()) {
                Pair<Integer, Integer> indexPair = indexesByNumber.getValue();
                sb.append(grill[indexPair.getObject1()][indexPair.getObject2()]);
            }
            grill = matrixRotator.rotate(grill);
        }
        return sb.toString();
    }

    private List<String> splitMessageIntoParts(String message) {
        final int numberOfGrills = getNumberOfGrills(message);
        final List<String> messageParts = new ArrayList<>();

        for (int i = 0; i < numberOfGrills; i++) {
            final int messagePartLength = i * MATRIX_ELEMENT_AMOUNT;
            String messagePart;
            if (i == numberOfGrills - 1) {
                messagePart = addFillers(message, messagePartLength);
            } else {
                messagePart = message.substring(messagePartLength, messagePartLength + MATRIX_ELEMENT_AMOUNT);
            }
            messageParts.add(messagePart);
        }
        return messageParts;
    }

    private String addFillers(String message, int messagePartLength) {
        String messagePart = message.substring(messagePartLength);
        int fillerCount = MATRIX_ELEMENT_AMOUNT - messagePart.length();

        messagePart = messagePart.concat(new String(
                new char[fillerCount]).replace(STRING_TERMINATOR, FILLER_CHAR)
        );
        return messagePart;
    }

    private int getNumberOfGrills(String message) {
        final int messageLength = message.length();
        int numberOfGrills = messageLength / MATRIX_ELEMENT_AMOUNT;

        if (messageLength % 16 != 0) {
            numberOfGrills++;
        }
        return numberOfGrills;
    }

}
