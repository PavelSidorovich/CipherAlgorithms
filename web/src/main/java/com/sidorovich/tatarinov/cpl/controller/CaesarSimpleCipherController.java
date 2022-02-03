package com.sidorovich.tatarinov.cpl.controller;

import com.sidorovich.tatarinov.cpl.dto.CaesarSimpleDto;
import com.sidorovich.tatarinov.cpl.enc.CaesarCipher;
import com.sidorovich.tatarinov.cpl.model.EncryptionModel;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/caesar-simple", produces = MediaType.APPLICATION_JSON_VALUE)
public class CaesarSimpleCipherController {

    private final CaesarCipher caesarCipher;
    private final ModelMapper modelMapper;

    public CaesarSimpleCipherController(CaesarCipher simpleCaesar, ModelMapper modelMapper) {
        this.caesarCipher = simpleCaesar;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EncryptionModel<Integer> process(
            @Valid @RequestBody CaesarSimpleDto message, @RequestParam boolean encrypt) {
        EncryptionModel<Integer> encryptionModel = new EncryptionModel<>("", message.getKey());
        modelMapper.map(message, encryptionModel);

        return encrypt
                ? caesarCipher.encrypt(encryptionModel)
                : caesarCipher.decrypt(encryptionModel);
    }

}
