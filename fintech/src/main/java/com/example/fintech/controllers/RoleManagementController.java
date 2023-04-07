package com.example.fintech.controllers;

import com.example.fintech.DTOs.RequestDTO.RoleManagementRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.RoleManagementInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.RoleManagementListDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.services.RoleManagementService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/role-management", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@Tag(name = "Role Management API")
@SecurityRequirement(name = "bearer-key")
public class RoleManagementController {

    @Autowired
    RoleManagementService roleManagementService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void create(@RequestBody RoleManagementRequestDTO dto,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        roleManagementService.create(dto, currentUser);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@PathVariable(name = "id") String id,
                       @RequestBody RoleManagementRequestDTO dto,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        roleManagementService.update(id, dto, currentUser);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable(name = "id") String id,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        roleManagementService.delete(id, currentUser);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoleManagementInfoDTO info(@RequestParam(name = "id") String id,
                                      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        return roleManagementService.info(id, currentUser);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoleManagementListDTO list(@RequestParam(name = "name", required = false) String name,
                                      @RequestParam(name = "page") Integer page,
                                      @RequestParam(name = "pageSize") Integer pageSize,
                                      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        return roleManagementService.list(name, page, pageSize, currentUser);
    }

}
