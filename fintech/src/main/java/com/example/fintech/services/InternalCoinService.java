package com.example.fintech.services;

import com.example.fintech.DTOs.RequestDTO.InternalCoinRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.InternalCoinInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.InternalCoinListDTO;
import com.example.fintech.authentications.UserPrincipal;

public interface InternalCoinService {
    void create(InternalCoinRequestDTO dto, UserPrincipal currentUser);

    void update(String id, InternalCoinRequestDTO dto, UserPrincipal currentUser);

    void delete(String id, UserPrincipal currentUser);

    InternalCoinInfoDTO info(String id, UserPrincipal currentUser);

    InternalCoinListDTO list(String userName, String partnerName, Integer page, Integer pageSize, UserPrincipal currentUser);
}
