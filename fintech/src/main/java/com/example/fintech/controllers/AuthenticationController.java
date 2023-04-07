package com.example.fintech.controllers;

import com.example.fintech.DTOs.RequestDTO.LoginRequestDTO;
import com.example.fintech.DTOs.RequestDTO.NormalUserInfoRequestDTO;
import com.example.fintech.DTOs.RequestDTO.TokenDTO;
import com.example.fintech.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@Tag(name = "Authentication API")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/authentication/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login")
    public TokenDTO login(@RequestBody LoginRequestDTO loginRequest,
                          HttpServletRequest request) {
        return authenticationService.login(loginRequest, request);
    }

    @RequestMapping(value = "/authentication/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register")
    public void register(@RequestBody NormalUserInfoRequestDTO dto) {
        
    }
}
