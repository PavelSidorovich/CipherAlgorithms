package com.sidorovich.tatarinov.cpl.controller;

import com.sidorovich.tatarinov.cpl.dto.KeywordDto;
import com.sidorovich.tatarinov.cpl.enc.KeywordCipher;
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
@RequestMapping(value = "/keyword", produces = MediaType.APPLICATION_JSON_VALUE)
public class KeywordCipherController {

    private final KeywordCipher keywordCipher;
    private final ModelMapper modelMapper;

    public KeywordCipherController(KeywordCipher keywordCipher, ModelMapper modelMapper) {
        this.keywordCipher = keywordCipher;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EncryptionModel<String> process(
            @Valid @RequestBody KeywordDto message, @RequestParam boolean encrypt) {
        EncryptionModel<String> encryptionModel = new EncryptionModel<>("", message.getKey());
        modelMapper.map(message, encryptionModel);

        return encrypt
                ? keywordCipher.encrypt(encryptionModel)
                : keywordCipher.decrypt(encryptionModel);
    }

}