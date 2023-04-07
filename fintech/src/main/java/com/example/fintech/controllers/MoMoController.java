package com.example.fintech.controllers;

import com.example.fintech.DTOs.RequestDTO.MoMoPaymentRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.MoMoPaymentResponseDTO;
import com.example.fintech.services.MoMoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@Tag(name = "Mo mo API")
public class MoMoController {
    @Autowired
    MoMoService moMoService;

    @RequestMapping(value = "/payment/momo/create-transaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MoMoPaymentResponseDTO createTransaction(@RequestBody MoMoPaymentRequestDTO dto) {
        return moMoService.createTransaction(dto);
    }
}
