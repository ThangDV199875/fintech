package com.example.fintech.services;

import com.example.fintech.DTOs.RequestDTO.PartnerRequestDTO;
import com.example.fintech.authentications.UserPrincipal;

public interface PartnerService {
    void create(PartnerRequestDTO dto, UserPrincipal currentUser);
}
