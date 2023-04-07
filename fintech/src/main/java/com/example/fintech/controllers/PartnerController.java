package com.example.fintech.controllers;

import com.example.fintech.DTOs.RequestDTO.PartnerRequestDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.entities.Partner;
import com.example.fintech.services.PartnerService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/partner", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@Tag(name = "Partner API")
@SecurityRequirement(name = "bearer-key")
public class PartnerController {
    @Autowired
    PartnerService partnerService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void create(@RequestBody PartnerRequestDTO dto,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        partnerService.create(dto, currentUser);
    }
}
