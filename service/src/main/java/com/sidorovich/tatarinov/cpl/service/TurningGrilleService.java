package com.sidorovich.tatarinov.cpl.service;

import com.sidorovich.tatarinov.cpl.model.EncryptionModel;

public interface TurningGrilleService
        extends CipherService<EncryptionModel<String>, EncryptionModel<Integer[][]>> {
}
