package com.example.fintech.services.impl;

import com.example.fintech.DTOs.RequestDTO.RoleManagementRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.RoleManagementInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.RoleManagementListDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.entities.AccessPermission;
import com.example.fintech.entities.RoleManagement;
import com.example.fintech.entities.User;
import com.example.fintech.enums.StatusEnum;
import com.example.fintech.repositories.AccessPermissionRepository;
import com.example.fintech.repositories.RoleManagementRepository;
import com.example.fintech.repositories.UserRepository;
import com.example.fintech.services.RoleManagementService;
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
public class RoleManagementServiceImpl implements RoleManagementService {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    RoleManagementRepository roleManagementRepository;

    @Autowired
    AccessPermissionRepository accessPermissionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void create(RoleManagementRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        for (String accessPermission : dto.getAccessPermissions()) {
            AccessPermission checkAccessPermission = accessPermissionRepository.findByName(accessPermission);
            if (checkAccessPermission == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access permission does not exist in the system!");
        }

        RoleManagement checkData = roleManagementRepository.findByName(dto.getName().trim());
        if (checkData != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role is already in the system!");

        RoleManagement roleManagement = mapper.map(dto, RoleManagement.class);
        roleManagement.setStatus(StatusEnum.ACTIVE);
        roleManagement.setCreatedUser(user.getId());
        roleManagement.setCreatedDate(LocalDateTime.now());
        roleManagementRepository.save(roleManagement);
    }

    @Override
    public void update(String id, RoleManagementRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        for (String accessPermission : dto.getAccessPermissions()) {
            AccessPermission checkAccessPermission = accessPermissionRepository.findByName(accessPermission);
            if (checkAccessPermission == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access permission does not exist in the system!");
        }

        RoleManagement roleManagement = roleManagementRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role does not exist!"));

        roleManagement.setName(dto.getName());
        roleManagement.setAccessPermissions(dto.getAccessPermissions());
        roleManagement.setUpdatedDate(LocalDateTime.now());
        roleManagement.setUpdatedUser(user.getId());
        roleManagementRepository.save(roleManagement);

    }

    @Override
    public void delete(String id, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        RoleManagement roleManagement = roleManagementRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role does not exist!"));

        roleManagementRepository.delete(roleManagement);
    }

    @Override
    public RoleManagementInfoDTO info(String id, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        RoleManagement roleManagement = roleManagementRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role does not exist!"));

        RoleManagementInfoDTO roleManagementInfoDTO = mapper.map(roleManagement, RoleManagementInfoDTO.class);
        if (roleManagement.getCreatedUser() == null) {
            roleManagementInfoDTO.setCreatedUser("");
        } else {
            User createdUser = userRepository.findById(roleManagement.getCreatedUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Created user does not exist!"));
            roleManagementInfoDTO.setCreatedUser(createdUser.getUsername());
        }

        if (roleManagement.getUpdatedUser() == null) {
            roleManagementInfoDTO.setUpdatedUser("");
        } else {
            User updatedUser = userRepository.findById(roleManagement.getUpdatedUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated user does not exist!"));
            roleManagementInfoDTO.setUpdatedUser(updatedUser.getUsername());
        }

        return roleManagementInfoDTO;
    }

    @Override
    public RoleManagementListDTO list(String name, Integer page, Integer pageSize, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdDate");
        List<RoleManagementInfoDTO> roleManagementInfoDTOList = new ArrayList<>();
        int totalPage = 0;
        long totalSize = 0;
        if (name == null) {
            Page<RoleManagement> roleManagementPage = roleManagementRepository.findAll(pageable);
            for (RoleManagement roleManagement : roleManagementPage) {
                RoleManagementInfoDTO roleManagementInfoDTO = mapper.map(roleManagement, RoleManagementInfoDTO.class);
                if (roleManagement.getCreatedUser() == null)
                    roleManagementInfoDTO.setCreatedUser("");
                else {
                    User createdUser = userRepository.findUserById(roleManagement.getCreatedUser());
                    roleManagementInfoDTO.setCreatedUser(createdUser.getUsername());
                }
                User updatedUser = userRepository.findUserById(roleManagement.getUpdatedUser());
                if (updatedUser == null)
                    roleManagementInfoDTO.setUpdatedUser("");
                else
                    roleManagementInfoDTO.setUpdatedUser(updatedUser.getUsername());

                roleManagementInfoDTOList.add(roleManagementInfoDTO);
            }
            totalPage = roleManagementPage.getTotalPages();
            totalSize = roleManagementPage.getTotalElements();
        } else {
            Page<RoleManagement> roleManagementPage = roleManagementRepository.getByName(name, pageable);
            for (RoleManagement roleManagement : roleManagementPage) {
                RoleManagementInfoDTO roleManagementInfoDTO = mapper.map(roleManagement, RoleManagementInfoDTO.class);
                User createdUser = userRepository.findUserById(roleManagement.getCreatedUser());
                roleManagementInfoDTO.setCreatedUser(createdUser.getUsername());
                User updatedUser = userRepository.findUserById(roleManagement.getUpdatedUser());
                if (updatedUser == null)
                    roleManagementInfoDTO.setUpdatedUser("");
                else
                    roleManagementInfoDTO.setUpdatedUser(updatedUser.getUsername());

                roleManagementInfoDTOList.add(roleManagementInfoDTO);
            }
            totalPage = roleManagementPage.getTotalPages();
            totalSize = roleManagementPage.getTotalElements();
        }

        RoleManagementListDTO roleManagementListDTO = new RoleManagementListDTO();
        roleManagementListDTO.setRoleManagementInfoDTOS(roleManagementInfoDTOList);
        roleManagementListDTO.setTotalPage(totalPage);
        roleManagementListDTO.setTotalSize(totalSize);
        return roleManagementListDTO;
    }

}
