package com.example.fintech.services;

import com.example.fintech.DTOs.RequestDTO.ApiManagementRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.ApiManagementInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.ApiManagementListDTO;
import com.example.fintech.authentications.UserPrincipal;

public interface ApiManagementService {

    void create(ApiManagementRequestDTO dto, UserPrincipal currentUser);

    void update(String id, ApiManagementRequestDTO dto, UserPrincipal currentUser);

    void delete(String id, UserPrincipal currentUser);

    ApiManagementInfoDTO info(String id, UserPrincipal currentUser);

    ApiManagementListDTO list(String name, Integer page, Integer pageSize, UserPrincipal currentUser);
}
