package com.example.fintech.controllers;

import com.example.fintech.DTOs.RequestDTO.NormalUserInfoRequestDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@Tag(name = "Mo mo API")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable(name = "id") String id,
                       @RequestBody NormalUserInfoRequestDTO dto,
                       @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal currentUser) {
        userService.update(id, dto, currentUser);
    }
}
