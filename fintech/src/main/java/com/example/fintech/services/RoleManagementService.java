package com.example.fintech.services;

import com.example.fintech.DTOs.RequestDTO.RoleManagementRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.RoleManagementInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.RoleManagementListDTO;
import com.example.fintech.authentications.UserPrincipal;

public interface RoleManagementService {

    void create(RoleManagementRequestDTO dto, UserPrincipal currentUser);

    void update(String id, RoleManagementRequestDTO dto, UserPrincipal currentUser);

    void delete(String id, UserPrincipal currentUser);

    RoleManagementInfoDTO info(String id, UserPrincipal currentUser);

    RoleManagementListDTO list(String name, Integer page, Integer pageSize, UserPrincipal currentUser);
}
