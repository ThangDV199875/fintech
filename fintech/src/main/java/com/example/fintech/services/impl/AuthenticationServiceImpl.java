package com.example.fintech.services.impl;

import com.example.fintech.DTOs.RequestDTO.LoginRequestDTO;
import com.example.fintech.DTOs.RequestDTO.NormalUserInfoRequestDTO;
import com.example.fintech.DTOs.RequestDTO.TokenDTO;
import com.example.fintech.authentications.TokenProvider;
import com.example.fintech.authentications.TokenUser;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.entities.RefreshToken;
import com.example.fintech.entities.User;
import com.example.fintech.enums.StatusEnum;
import com.example.fintech.repositories.RefreshTokenRepository;
import com.example.fintech.repositories.UserRepository;
import com.example.fintech.services.AuthenticationService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    PasswordEncoder pwdEnc;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    DozerBeanMapper mapper;

    @Value("${authentication.token.jwt.expire}")
    private int tokenTimeout;

    @Override
    public TokenDTO login(LoginRequestDTO loginRequest, HttpServletRequest request) {

        User user = userRepository.findByUsernameOrEmail(loginRequest.getUsername().toLowerCase(), loginRequest.getUsername().toLowerCase());
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or Password Invalid!");

//        if (StatusEnum.PENDING.equals(user.getStatus()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is not actived!");
//
//        if (!StatusEnum.ACTIVE.equals(user.getStatus()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is blocked!");

        if (!pwdEnc.matches(loginRequest.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Email or Password Invalid!");

        return authorizeUser(user);
    }

    @Override
    public void register(NormalUserInfoRequestDTO dto) {
        User checkData = userRepository.findByUsername(dto.getUsername());
        if (checkData != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!");

        List<String> roles = new ArrayList<>();
        roles.add("USER");

        User user = mapper.map(dto, User.class);
        user.setPassword(pwdEnc.encode(dto.getPassword()));
        user.setRoles(roles);
        user.setCreatedDate(LocalDateTime.now());
        userRepository.save(user);
    }

    private TokenDTO authorizeUser(User user) {
        TokenUser tokenUser = new TokenUser();

        tokenUser.setId(user.getId().toHexString());
        tokenUser.setEmail(user.getEmail());
        List<String> roles = new ArrayList<>(user.getRoles());

        roles.remove(null);
        roles.remove("");
        tokenUser.setAuthorities(roles);
        UserPrincipal userPrincipal = UserPrincipal.create(tokenUser);

        String token = tokenProvider.issueToken(userPrincipal);

        TokenDTO tokenDTO = new TokenDTO();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getId());
        refreshToken.setStatus(StatusEnum.ACTIVE);
        refreshTokenRepository.save(refreshToken);

        tokenDTO.setName(user.getName());
        tokenDTO.setToken(token);
        tokenDTO.setBearerToken("Bearer " + token);
        tokenDTO.setRoles(roles);
        tokenDTO.setRefreshToken(refreshToken.getId().toHexString());
        tokenDTO.setExpire(LocalDateTime.now().plusSeconds(tokenTimeout).toEpochSecond(ZoneOffset.UTC));

        return tokenDTO;
    }
}
