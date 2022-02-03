package com.sidorovich.tatarinov.cpl.util.impl;

import com.sidorovich.tatarinov.cpl.util.MatrixRotator;
import org.springframework.stereotype.Service;

@Service
public class MatrixRotatorImpl<T> implements MatrixRotator<T> {

    @Override
    public T[][] rotate(T[][] matrix) {
        final int size = matrix.length;

        for (int i = 0; i < size / 2; i++) {
            for (int j = i; j < size - i - 1; j++) {
                final T tmp = matrix[i][j];

                matrix[i][j] = matrix[j][size - 1 - i];
                matrix[j][size - 1 - i] = matrix[size - 1 - i][size - 1 - j];
                matrix[size - 1 - i][size - 1 - j] = matrix[size - 1 - j][i];
                matrix[size - 1 - j][i] = tmp;
            }
        }
        return matrix;
    }

}
