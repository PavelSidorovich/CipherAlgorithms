package com.sidorovich.tatarinov.cpl.controller;

import com.sidorovich.tatarinov.cpl.dto.RailFenceDto;
import com.sidorovich.tatarinov.cpl.enc.RailFenceCipher;
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
@RequestMapping(value = "/rail-fence", produces = MediaType.APPLICATION_JSON_VALUE)
public class RailFenceCipherController {

    private final RailFenceCipher railFenceCipher;
    private final ModelMapper modelMapper;

    public RailFenceCipherController(RailFenceCipher railFenceCipher, ModelMapper modelMapper) {
        this.railFenceCipher = railFenceCipher;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EncryptionModel<Integer> process(
            @Valid @RequestBody RailFenceDto message, @RequestParam boolean encrypt) {
        EncryptionModel<Integer> encryptionModel = new EncryptionModel<>("", message.getKey());
        modelMapper.map(message, encryptionModel);

        return encrypt
                ? railFenceCipher.encrypt(encryptionModel)
                : railFenceCipher.decrypt(encryptionModel);
    }

}
