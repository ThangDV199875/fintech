package com.example.fintech.controllers;

import com.example.fintech.DTOs.RequestDTO.InternalCoinRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.InternalCoinInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.InternalCoinListDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.services.InternalCoinService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/internal-coin", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@Tag(name = "Internal Coin")
@SecurityRequirement(name = "bearer-key")
public class InternalCoinController {

    @Autowired
    InternalCoinService internalCoinService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void create(@RequestBody InternalCoinRequestDTO dto,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        internalCoinService.create(dto, currentUser);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@PathVariable(name = "id") String id,
                       @RequestBody InternalCoinRequestDTO dto,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        internalCoinService.update(id, dto, currentUser);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable(name = "id") String id,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        internalCoinService.delete(id, currentUser);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public InternalCoinInfoDTO info(@RequestParam(name = "id") String id,
                                    @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        return internalCoinService.info(id, currentUser);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public InternalCoinListDTO list(@RequestParam(name = "userName") String userName,
                                    @RequestParam(name = "partnerName") String partnerName,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "pageSize") Integer pageSize,
                                    @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        return internalCoinService.list(userName, partnerName, page, pageSize, currentUser);
    }
}
