package com.sidorovich.tatarinov.cpl.controller;

import com.sidorovich.tatarinov.cpl.dto.CaesarAdvancedDto;
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
@RequestMapping(value = "/caesar-advanced", produces = MediaType.APPLICATION_JSON_VALUE)
public class CaesarAdvancedCipherController {

    private final CaesarCipher caesarCipher;
    private final ModelMapper modelMapper;

    public CaesarAdvancedCipherController(CaesarCipher advancedCaesar, ModelMapper modelMapper) {
        this.caesarCipher = advancedCaesar;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EncryptionModel<Integer> process(
            @Valid @RequestBody CaesarAdvancedDto message, @RequestParam boolean encrypt) {
        EncryptionModel<Integer> encryptionModel = new EncryptionModel<>("", message.getKey());
        modelMapper.map(message, encryptionModel);

        return encrypt
                ? caesarCipher.encrypt(encryptionModel)
                : caesarCipher.decrypt(encryptionModel);
    }

}
