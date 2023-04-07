package com.example.fintech.services;

import com.example.fintech.DTOs.RequestDTO.NormalUserInfoRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.NormalUserInfoDTO;
import com.example.fintech.authentications.UserPrincipal;

import java.util.List;

public interface UserService {
    void update(String id, NormalUserInfoRequestDTO dto, UserPrincipal currentUser);

    NormalUserInfoDTO info(String id, UserPrincipal currentUser);

    List<NormalUserInfoDTO> list(String name, String username, Integer page, Integer pageSize, UserPrincipal currentUser);
}
