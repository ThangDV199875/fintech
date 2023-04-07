package com.example.fintech.services.impl;

import com.example.fintech.DTOs.RequestDTO.NormalUserInfoRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.NormalUserInfoDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.entities.User;
import com.example.fintech.repositories.UserRepository;
import com.example.fintech.services.UserService;
import org.bson.types.ObjectId;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEnc;

    @Override
    public void update(String id, NormalUserInfoRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        User data = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!"));

        if (!data.getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not allowed to access!");

        data.setName(dto.getName());
        data.setUsername(dto.getUsername());
        data.setPassword(pwdEnc.encode(dto.getPassword()));
        data.setEmail(dto.getEmail());
        data.setPhone(dto.getPhone());
        userRepository.save(data);
    }

    @Override
    public NormalUserInfoDTO info(String id, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        User data = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!"));

        if (!data.getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not allowed to access!");

        NormalUserInfoDTO userInfoDTO = mapper.map(data, NormalUserInfoDTO.class);
        return userInfoDTO;
    }

    @Override
    public List<NormalUserInfoDTO> list(String name, String username, Integer page, Integer pageSize, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));


        return null;
    }
}
