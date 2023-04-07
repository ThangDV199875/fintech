package com.example.fintech.services.impl;

import com.example.fintech.DTOs.RequestDTO.AccessPermissionRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.AccessPermissionInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.AccessPermissionListDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.entities.AccessPermission;
import com.example.fintech.entities.User;
import com.example.fintech.enums.StatusEnum;
import com.example.fintech.repositories.AccessPermissionRepository;
import com.example.fintech.repositories.UserRepository;
import com.example.fintech.services.AccessPermissionService;
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
public class AccessPermissionServiceImpl implements AccessPermissionService {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccessPermissionRepository accessPermissionRepository;

    @Override
    public void create(AccessPermissionRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        AccessPermission checkData = accessPermissionRepository.findByName(dto.getName());
        if (checkData != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access permission is already in the system!");

        AccessPermission accessPermission = new AccessPermission();
        accessPermission.setName(dto.getName());
        accessPermission.setStatus(StatusEnum.ACTIVE);
        accessPermission.setCreatedDate(LocalDateTime.now());
        accessPermission.setCreatedUser(user.getId());
        accessPermissionRepository.save(accessPermission);
    }

    @Override
    public void update(String id, AccessPermissionRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        AccessPermission checkData = accessPermissionRepository.findByName(dto.getName());
        if (checkData != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access permission is already in the system!");

        AccessPermission accessPermission = accessPermissionRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access permission does not exist!"));

        accessPermission.setName(dto.getName());
        accessPermission.setUpdatedDate(LocalDateTime.now());
        accessPermission.setUpdatedUser(user.getId());
        accessPermissionRepository.save(accessPermission);
    }

    @Override
    public void delete(String id, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        AccessPermission accessPermission = accessPermissionRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access permission does not exist!"));

        accessPermissionRepository.delete(accessPermission);
    }

    @Override
    public AccessPermissionInfoDTO info(String id, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        AccessPermission accessPermission = accessPermissionRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access permission does not exist!"));

        User createdUser = userRepository.findById(accessPermission.getCreatedUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Created user does not exist!"));

        AccessPermissionInfoDTO accessPermissionInfoDTO = mapper.map(accessPermission, AccessPermissionInfoDTO.class);
        accessPermissionInfoDTO.setCreatedUser(createdUser.getUsername());
        if (accessPermission.getUpdatedUser() == null)
            accessPermissionInfoDTO.setUpdatedUser("");
        else {
            User updatedUser = userRepository.findById(accessPermission.getUpdatedUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated user does not exist!"));
            accessPermissionInfoDTO.setUpdatedUser(updatedUser.getUsername());
        }
        return accessPermissionInfoDTO;
    }

    @Override
    public AccessPermissionListDTO list(String name, Integer page, Integer pageSize, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdDate");
        List<AccessPermissionInfoDTO> accessPermissionInfoDTOList = new ArrayList<>();
        int totalPage = 0;
        long totalSize = 0;
        if (name == null) {
            Page<AccessPermission> accessPermissionPage = accessPermissionRepository.findAll(pageable);
            for (AccessPermission accessPermission : accessPermissionPage) {
                AccessPermissionInfoDTO accessPermissionInfoDTO = mapper.map(accessPermission, AccessPermissionInfoDTO.class);
                if (accessPermission.getCreatedUser() == null) {
                    accessPermissionInfoDTO.setCreatedUser("");
                } else {
                    User createdUser = userRepository.findUserById(accessPermission.getCreatedUser());
                    accessPermissionInfoDTO.setCreatedUser(createdUser.getUsername());
                }
                User updatedUser = userRepository.findUserById(accessPermission.getUpdatedUser());
                if (updatedUser == null)
                    accessPermissionInfoDTO.setUpdatedUser("");
                else
                    accessPermissionInfoDTO.setUpdatedUser(updatedUser.getUsername());

                accessPermissionInfoDTOList.add(accessPermissionInfoDTO);
            }
            totalPage = accessPermissionPage.getTotalPages();
            totalSize =accessPermissionPage.getTotalElements();
        } else {
            Page<AccessPermission> accessPermissionPage = accessPermissionRepository.getByName(name, pageable);
            for (AccessPermission accessPermission : accessPermissionPage) {
                AccessPermissionInfoDTO accessPermissionInfoDTO = mapper.map(accessPermission, AccessPermissionInfoDTO.class);
                User createdUser = userRepository.findUserById(accessPermission.getCreatedUser());
                accessPermissionInfoDTO.setCreatedUser(createdUser.getUsername());
                User updatedUser = userRepository.findUserById(accessPermission.getUpdatedUser());
                if (updatedUser == null)
                    accessPermissionInfoDTO.setUpdatedUser("");
                else
                    accessPermissionInfoDTO.setUpdatedUser(updatedUser.getUsername());

                accessPermissionInfoDTOList.add(accessPermissionInfoDTO);
            }
            totalPage = accessPermissionPage.getTotalPages();
            totalSize =accessPermissionPage.getTotalElements();
        }

        AccessPermissionListDTO accessPermissionListDTO = new AccessPermissionListDTO();
        accessPermissionListDTO.setAccessPermissionInfoDTOS(accessPermissionInfoDTOList);
        accessPermissionListDTO.setTotalPage(totalPage);
        accessPermissionListDTO.setTotalSize(totalSize);
        return accessPermissionListDTO;
    }

}
