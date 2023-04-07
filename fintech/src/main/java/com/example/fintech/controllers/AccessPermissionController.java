package com.example.fintech.controllers;

import com.example.fintech.DTOs.RequestDTO.AccessPermissionRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.AccessPermissionInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.AccessPermissionListDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.services.AccessPermissionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/access-permission", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@Tag(name = "Access Permission API")
@SecurityRequirement(name = "bearer-key")
public class AccessPermissionController {

    @Autowired
    AccessPermissionService accessPermissionService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void create(@RequestBody AccessPermissionRequestDTO dto,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        accessPermissionService.create(dto, currentUser);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@PathVariable(name = "id") String id,
                       @RequestBody AccessPermissionRequestDTO dto,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        accessPermissionService.update(id, dto, currentUser);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable(name = "id") String id,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        accessPermissionService.delete(id, currentUser);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPermissionInfoDTO info(@RequestParam(name = "id") String id,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        return accessPermissionService.info(id, currentUser);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPermissionListDTO list(@RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "page") Integer page,
                                        @RequestParam(name = "pageSize") Integer pageSize,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        return accessPermissionService.list(name, page, pageSize, currentUser);
    }
}
