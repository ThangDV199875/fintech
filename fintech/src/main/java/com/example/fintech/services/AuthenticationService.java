package com.example.fintech.services;

import com.example.fintech.DTOs.RequestDTO.LoginRequestDTO;
import com.example.fintech.DTOs.RequestDTO.NormalUserInfoRequestDTO;
import com.example.fintech.DTOs.RequestDTO.TokenDTO;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    TokenDTO login(LoginRequestDTO loginRequest, HttpServletRequest request);

    void register(NormalUserInfoRequestDTO dto);

}
