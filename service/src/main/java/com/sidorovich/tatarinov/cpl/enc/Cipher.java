package com.sidorovich.tatarinov.cpl.enc;

public interface Cipher<T> {

    T encrypt(T input);

    T decrypt(T input);

}
