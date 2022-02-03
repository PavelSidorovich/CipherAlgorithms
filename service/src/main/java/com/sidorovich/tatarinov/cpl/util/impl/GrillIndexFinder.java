package com.sidorovich.tatarinov.cpl.util.impl;

import com.sidorovich.tatarinov.cpl.model.Pair;
import com.sidorovich.tatarinov.cpl.util.IndexFinder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GrillIndexFinder implements IndexFinder {

    private static final int NUMBER_AMOUNT = 4;

    @Override
    public Map<Integer, Pair<Integer, Integer>> getIndexes(Integer[][] grill) {
        Map<Integer, Pair<Integer, Integer>> indexesByNumbers = new HashMap<>();

        for (int i = 0; i < grill.length; i++) {
            for (int j = 0; j < grill[0].length; j++) {
                Pair<Integer, Integer> indexes = indexesByNumbers.get(grill[i][j]);
                if (indexes == null) {
                    indexesByNumbers.put(grill[i][j], new Pair<>(i, j));
                }
                if (indexesByNumbers.size() == NUMBER_AMOUNT) {
                    return indexesByNumbers;
                }
            }
        }
        return indexesByNumbers;
    }

}
