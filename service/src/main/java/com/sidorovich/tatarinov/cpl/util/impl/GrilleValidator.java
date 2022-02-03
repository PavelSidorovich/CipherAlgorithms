package com.sidorovich.tatarinov.cpl.util.impl;

import com.sidorovich.tatarinov.cpl.exception.InvalidGrilleKeyException;
import com.sidorovich.tatarinov.cpl.model.EncryptionModelFields;
import com.sidorovich.tatarinov.cpl.util.MatrixRotator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GrilleValidator {

    private static final int MATRIX_SIZE = 4;
    private static final String ROW_REGEX = "\\{(\\d), (\\d), (\\d), (\\d)}";

    private final MatrixRotator<Integer> matrixRotator;

    public GrilleValidator(MatrixRotator<Integer> matrixRotator) {
        this.matrixRotator = matrixRotator;
    }

    public Integer[][] extract(String key) {
        Matcher matcher = Pattern.compile(ROW_REGEX).matcher(key);
        Integer[][] grille = new Integer[MATRIX_SIZE][MATRIX_SIZE];
        int row = 0;

        while (matcher.find()) {
            int col = 0;
            grille[row][col] = Integer.valueOf(matcher.group(++col));
            grille[row][col] = Integer.valueOf(matcher.group(++col));
            grille[row][col] = Integer.valueOf(matcher.group(++col));
            grille[row][col] = Integer.valueOf(matcher.group(++col));
            row++;
        }
        if (row != MATRIX_SIZE) {
            throw new InvalidGrilleKeyException(EncryptionModelFields.KEY.getFieldName());
        }
        validate(grille);

        return grille;
    }

    private void validate(Integer[][] grille) {
        Integer[][] rotated = matrixRotator.rotate(grilleDeepCopy(grille));
        for (int i = 0; i < MATRIX_SIZE; i++) {
            if (!Arrays.equals(grille[i], rotated[i])) {
                throw new InvalidGrilleKeyException(EncryptionModelFields.KEY.getFieldName());
            }
            rotated = matrixRotator.rotate(rotated);
        }
    }

    private Integer[][] grilleDeepCopy(Integer[][] toCopy) {
        Integer[][] copy = new Integer[MATRIX_SIZE][MATRIX_SIZE];

        for (int i = 0; i < MATRIX_SIZE; i++) {
            System.arraycopy(toCopy[i], 0, copy[i], 0, MATRIX_SIZE);
        }
        return copy;
    }

}
