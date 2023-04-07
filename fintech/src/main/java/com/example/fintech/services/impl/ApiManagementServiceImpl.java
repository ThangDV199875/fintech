package com.example.fintech.services.impl;

import com.example.fintech.DTOs.RequestDTO.ApiManagementRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.ApiManagementInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.ApiManagementListDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.entities.ApiManagement;
import com.example.fintech.entities.User;
import com.example.fintech.enums.StatusEnum;
import com.example.fintech.repositories.ApiManagementRepository;
import com.example.fintech.repositories.UserRepository;
import com.example.fintech.services.ApiManagementService;
import org.bson.types.ObjectId;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiManagementServiceImpl implements ApiManagementService {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    ApiManagementRepository apiManagementRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void create(ApiManagementRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        ApiManagement checkData = apiManagementRepository.findByName(dto.getName().trim());
        if (checkData != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Api is already in the system!");

        ApiManagement apiManagement = mapper.map(dto, ApiManagement.class);
        apiManagement.setStatus(StatusEnum.ACTIVE);
        apiManagement.setCreatedDate(LocalDateTime.now());
        apiManagement.setCreatedUser(user.getId());
        apiManagementRepository.save(apiManagement);
    }

    @Override
    public void update(String id, ApiManagementRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        ApiManagement checkData = apiManagementRepository.findByName(dto.getName().trim());
        if (checkData != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Api is already in the system!");

        ApiManagement apiManagement = apiManagementRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Api does not exist!"));

        apiManagement.setName(dto.getName());
        apiManagement.setApiTargets(dto.getApiTargets());
        apiManagement.setRoles(dto.getRoles());
        apiManagement.setUpdatedDate(LocalDateTime.now());
        apiManagement.setUpdatedUser(user.getId());
        apiManagementRepository.save(apiManagement);
    }

    @Override
    public void delete(String id, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        ApiManagement apiManagement = apiManagementRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Api does not exist!"));

        apiManagementRepository.delete(apiManagement);
    }

    @Override
    public ApiManagementInfoDTO info(String id, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        ApiManagement apiManagement = apiManagementRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Api does not exist!"));

        User createdUser = userRepository.findById(apiManagement.getCreatedUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Created user does not exist!"));

        ApiManagementInfoDTO apiManagementInfoDTO = mapper.map(apiManagement, ApiManagementInfoDTO.class);
        apiManagementInfoDTO.setCreatedUser(createdUser.getUsername());
        if (apiManagement.getUpdatedUser() == null)
            apiManagementInfoDTO.setUpdatedUser("");
        else {
            User updatedUser = userRepository.findById(apiManagement.getUpdatedUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated user does not exist!"));
            apiManagementInfoDTO.setUpdatedUser(updatedUser.getUsername());
        }

        return apiManagementInfoDTO;
    }

    @Override
    public ApiManagementListDTO list(String name, Integer page, Integer pageSize, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdDate");
        List<ApiManagementInfoDTO> apiManagementInfoDTOList = new ArrayList<>();
        int totalPage = 0;
        long totalSize = 0;
        if (name == null) {
            Page<ApiManagement> apiManagementPage = apiManagementRepository.findAll(pageable);
            for (ApiManagement apiManagement : apiManagementPage) {
                ApiManagementInfoDTO apiManagementInfoDTO = mapper.map(apiManagement, ApiManagementInfoDTO.class);
                User createdUser = userRepository.findUserById(apiManagement.getCreatedUser());
                apiManagementInfoDTO.setCreatedUser(createdUser.getUsername());
                User updatedUser = userRepository.findUserById(apiManagement.getUpdatedUser());
                if (updatedUser == null)
                    apiManagementInfoDTO.setUpdatedUser("");
                else
                    apiManagementInfoDTO.setUpdatedUser(updatedUser.getUsername());
                apiManagementInfoDTOList.add(apiManagementInfoDTO);
            }

            totalPage = apiManagementPage.getTotalPages();
            totalSize = apiManagementPage.getTotalElements();
        } else {
            Page<ApiManagement> apiManagementPage = apiManagementRepository.getByName(name, pageable);
            for (ApiManagement apiManagement : apiManagementPage) {
                ApiManagementInfoDTO apiManagementInfoDTO = mapper.map(apiManagement, ApiManagementInfoDTO.class);
                User createdUser = userRepository.findUserById(apiManagement.getCreatedUser());
                apiManagementInfoDTO.setCreatedUser(createdUser.getUsername());
                User updatedUser = userRepository.findUserById(apiManagement.getUpdatedUser());
                if (updatedUser == null)
                    apiManagementInfoDTO.setUpdatedUser("");
                else
                    apiManagementInfoDTO.setUpdatedUser(updatedUser.getUsername());
                apiManagementInfoDTOList.add(apiManagementInfoDTO);
            }

            totalPage = apiManagementPage.getTotalPages();
            totalSize = apiManagementPage.getTotalElements();
        }

        ApiManagementListDTO apiManagementListDTO = new ApiManagementListDTO();
        apiManagementListDTO.setApiManagementInfoDTOS(apiManagementInfoDTOList);
        apiManagementListDTO.setTotalPage(totalPage);
        apiManagementListDTO.setTotalSize(totalSize);
        return apiManagementListDTO;
    }
}
