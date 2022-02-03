package com.sidorovich.tatarinov.cpl.service;

public interface CipherService<T, R> {

    R encrypt(T input);

    R decrypt(T input);

}
