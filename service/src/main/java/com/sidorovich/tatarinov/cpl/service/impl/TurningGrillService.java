package com.sidorovich.tatarinov.cpl.service.impl;

import com.sidorovich.tatarinov.cpl.enc.TurningGrilleCipher;
import com.sidorovich.tatarinov.cpl.exception.CorruptedMessageException;
import com.sidorovich.tatarinov.cpl.model.EncryptionModel;
import com.sidorovich.tatarinov.cpl.service.TurningGrilleService;
import com.sidorovich.tatarinov.cpl.util.impl.GrilleValidator;
import org.springframework.stereotype.Service;

@Service
public class TurningGrillService implements TurningGrilleService {

    private final TurningGrilleCipher turningGrilleCipher;
    private final GrilleValidator grilleValidator;

    public TurningGrillService(TurningGrilleCipher turningGrilleCipher,
                               GrilleValidator grilleValidator) {
        this.turningGrilleCipher = turningGrilleCipher;
        this.grilleValidator = grilleValidator;
    }

    @Override
    public EncryptionModel<Integer[][]> encrypt(EncryptionModel<String> input) {
        Integer[][] grille = grilleValidator.extract(input.getKey());

        return turningGrilleCipher.encrypt(new EncryptionModel<>(input.getMessage(), grille));
    }

    @Override
    public EncryptionModel<Integer[][]> decrypt(EncryptionModel<String> input) {
        Integer[][] grille = grilleValidator.extract(input.getKey());

        if (input.getMessage().length() % 16 != 0) {
            throw new CorruptedMessageException(input.getMessage().length());
        }
        return turningGrilleCipher.decrypt(new EncryptionModel<>(input.getMessage(), grille));
    }
}
