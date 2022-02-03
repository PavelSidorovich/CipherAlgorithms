package com.sidorovich.tatarinov.cpl.util;

import com.sidorovich.tatarinov.cpl.model.Pair;

import java.util.Map;

public interface IndexFinder {

    Map<Integer, Pair<Integer, Integer>> getIndexes(Integer[][] matrix);

}
