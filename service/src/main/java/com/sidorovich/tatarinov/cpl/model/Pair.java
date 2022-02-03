package com.sidorovich.tatarinov.cpl.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Pair<T, F> {

    private final T object1;
    private final F object2;

}
