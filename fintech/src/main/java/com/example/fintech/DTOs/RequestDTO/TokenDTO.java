package com.example.fintech.DTOs.RequestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {

    private String name;

    private Long expire;

    private String token;

    private String bearerToken;

    private List<String> roles;

    private String refreshToken;

}
