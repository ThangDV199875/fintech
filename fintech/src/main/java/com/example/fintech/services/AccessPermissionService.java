package com.example.fintech.services;

import com.example.fintech.DTOs.RequestDTO.AccessPermissionRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.AccessPermissionInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.AccessPermissionListDTO;
import com.example.fintech.authentications.UserPrincipal;

public interface AccessPermissionService {

    void create(AccessPermissionRequestDTO dto, UserPrincipal currentUser);

    void update(String id, AccessPermissionRequestDTO dto, UserPrincipal currentUser);

    void delete(String id, UserPrincipal currentUser);

    AccessPermissionInfoDTO info(String id, UserPrincipal currentUser);

    AccessPermissionListDTO list(String name, Integer page, Integer pageSize, UserPrincipal currentUser);

}
