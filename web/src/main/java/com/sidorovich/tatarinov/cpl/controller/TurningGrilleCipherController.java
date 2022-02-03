package com.sidorovich.tatarinov.cpl.controller;

import com.sidorovich.tatarinov.cpl.dto.TurningGrilleCipherDto;
import com.sidorovich.tatarinov.cpl.model.EncryptionModel;
import com.sidorovich.tatarinov.cpl.service.TurningGrilleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/turning-grille", produces = MediaType.APPLICATION_JSON_VALUE)
public class TurningGrilleCipherController {

    private final TurningGrilleService turningGrilleService;
    private final ModelMapper modelMapper;

    public TurningGrilleCipherController(TurningGrilleService turningGrilleService, ModelMapper modelMapper) {
        this.turningGrilleService = turningGrilleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EncryptionModel<Integer[][]> process(
            @Valid @RequestBody TurningGrilleCipherDto message, @RequestParam boolean encrypt) {
        EncryptionModel<String> encryptionModel = new EncryptionModel<>("", message.getKey());
        modelMapper.map(message, encryptionModel);

        return encrypt
                ? turningGrilleService.encrypt(encryptionModel)
                : turningGrilleService.decrypt(encryptionModel);
    }

}
