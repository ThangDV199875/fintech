package com.example.fintech.authentications;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenUser {
    private String id;
    private String name;
    private String email;
    private Integer expire;
    private List<String> authorities;
}
