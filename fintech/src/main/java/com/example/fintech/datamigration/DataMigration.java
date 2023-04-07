package com.example.fintech.datamigration;

import com.example.fintech.authentications.TokenProvider;
import com.example.fintech.entities.AccessPermission;
import com.example.fintech.entities.ApiManagement;
import com.example.fintech.entities.RoleManagement;
import com.example.fintech.entities.User;
import com.example.fintech.enums.ApiTargetEnum;
import com.example.fintech.enums.StatusEnum;
import com.example.fintech.repositories.AccessPermissionRepository;
import com.example.fintech.repositories.ApiManagementRepository;
import com.example.fintech.repositories.RoleManagementRepository;
import com.example.fintech.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataMigration {
    @Autowired
    AccessPermissionRepository accessPermissionRepository;

    @Autowired
    RoleManagementRepository roleManagementRepository;

    @Autowired
    ApiManagementRepository apiManagementRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEnc;

    @Autowired
    private TokenProvider tokenProvider;

    @PostConstruct
    public void changeSet() {
        initData();
    }

    private void initData() {
        List<AccessPermission> accessPermissions = accessPermissionRepository.findAll();
        List<String> accessPermissionList = new ArrayList<>();
        for (AccessPermission accessPermission : accessPermissions) {
            accessPermissionList.add(accessPermission.getName());
        }

        if (!accessPermissionList.contains("CREATE")) {
            AccessPermission data = new AccessPermission();
            data.setName("CREATE");
            data.setStatus(StatusEnum.ACTIVE);
            data.setCreatedDate(LocalDateTime.now());
            accessPermissionRepository.save(data);
        }

        if (!accessPermissionList.contains("UPDATE")) {
            AccessPermission data = new AccessPermission();
            data.setName("UPDATE");
            data.setStatus(StatusEnum.ACTIVE);
            data.setCreatedDate(LocalDateTime.now());
            accessPermissionRepository.save(data);
        }

        if (!accessPermissionList.contains("DELETE")) {
            AccessPermission data = new AccessPermission();
            data.setName("DELETE");
            data.setStatus(StatusEnum.ACTIVE);
            data.setCreatedDate(LocalDateTime.now());
            accessPermissionRepository.save(data);
        }

        if (!accessPermissionList.contains("READ")) {
            AccessPermission data = new AccessPermission();
            data.setName("READ");
            data.setStatus(StatusEnum.ACTIVE);
            data.setCreatedDate(LocalDateTime.now());
            accessPermissionRepository.save(data);
        }

        RoleManagement checkRole = roleManagementRepository.findByName("ADMIN");
        if (checkRole == null) {
            List<String> accessPermissionRole = new ArrayList<>();
            accessPermissionRole.add("CREATE");
            accessPermissionRole.add("UPDATE");
            accessPermissionRole.add("DELETE");
            accessPermissionRole.add("READ");
            RoleManagement data = new RoleManagement();
            data.setName("ADMIN");
            data.setAccessPermissions(accessPermissionRole);
            data.setStatus(StatusEnum.ACTIVE);
            data.setCreatedDate(LocalDateTime.now());
            roleManagementRepository.save(data);
        }

        if (apiManagementRepository.findByName("/access-permission") == null) {
            ApiManagement apiManagement = new ApiManagement();
            apiManagement.setName("/access-permission");
            List<ApiTargetEnum> apiTargetEnums = new ArrayList<>();
            apiTargetEnums.add(ApiTargetEnum.PRIVATE);
            apiTargetEnums.add(ApiTargetEnum.PUBLIC);
            apiManagement.setApiTargets(apiTargetEnums);
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            apiManagement.setRoles(roles);
            apiManagement.setStatus(StatusEnum.ACTIVE);
            apiManagementRepository.save(apiManagement);
        }

        if (apiManagementRepository.findByName("/api-management") == null) {
            ApiManagement apiManagement = new ApiManagement();
            apiManagement.setName("/api-management");
            List<ApiTargetEnum> apiTargetEnums = new ArrayList<>();
            apiTargetEnums.add(ApiTargetEnum.PRIVATE);
            apiTargetEnums.add(ApiTargetEnum.PUBLIC);
            apiManagement.setApiTargets(apiTargetEnums);
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            apiManagement.setRoles(roles);
            apiManagement.setStatus(StatusEnum.ACTIVE);
            apiManagementRepository.save(apiManagement);
        }

        if (apiManagementRepository.findByName("/payment/momo/create-transaction") == null) {
            ApiManagement apiManagement = new ApiManagement();
            apiManagement.setName("/payment/momo/create-transaction");
            List<ApiTargetEnum> apiTargetEnums = new ArrayList<>();
            apiTargetEnums.add(ApiTargetEnum.PRIVATE);
            apiTargetEnums.add(ApiTargetEnum.PUBLIC);
            apiManagement.setApiTargets(apiTargetEnums);
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            apiManagement.setRoles(roles);
            apiManagement.setStatus(StatusEnum.ACTIVE);
            apiManagementRepository.save(apiManagement);
        }

        if (apiManagementRepository.findByName("/role-management") == null) {
            ApiManagement apiManagement = new ApiManagement();
            apiManagement.setName("/role-management");
            List<ApiTargetEnum> apiTargetEnums = new ArrayList<>();
            apiTargetEnums.add(ApiTargetEnum.PRIVATE);
            apiTargetEnums.add(ApiTargetEnum.PUBLIC);
            apiManagement.setApiTargets(apiTargetEnums);
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            apiManagement.setRoles(roles);
            apiManagement.setStatus(StatusEnum.ACTIVE);
            apiManagementRepository.save(apiManagement);
        }

        if (apiManagementRepository.findByName("/v3") == null) {
            ApiManagement apiManagement = new ApiManagement();
            apiManagement.setName("/v3");
            List<ApiTargetEnum> apiTargetEnums = new ArrayList<>();
            apiTargetEnums.add(ApiTargetEnum.PRIVATE);
            apiTargetEnums.add(ApiTargetEnum.PUBLIC);
            apiManagement.setApiTargets(apiTargetEnums);
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            apiManagement.setRoles(roles);
            apiManagement.setStatus(StatusEnum.ACTIVE);
            apiManagementRepository.save(apiManagement);
        }

        if (apiManagementRepository.findByName("/swagger-ui") == null) {
            ApiManagement apiManagement = new ApiManagement();
            apiManagement.setName("/swagger-ui");
            List<ApiTargetEnum> apiTargetEnums = new ArrayList<>();
            apiTargetEnums.add(ApiTargetEnum.PRIVATE);
            apiTargetEnums.add(ApiTargetEnum.PUBLIC);
            apiManagement.setApiTargets(apiTargetEnums);
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            apiManagement.setRoles(roles);
            apiManagement.setStatus(StatusEnum.ACTIVE);
            apiManagementRepository.save(apiManagement);
        }

        if (apiManagementRepository.findByName("/authentication/login") == null) {
            ApiManagement apiManagement = new ApiManagement();
            apiManagement.setName("/authentication/login");
            List<ApiTargetEnum> apiTargetEnums = new ArrayList<>();
            apiTargetEnums.add(ApiTargetEnum.PRIVATE);
            apiTargetEnums.add(ApiTargetEnum.PUBLIC);
            apiManagement.setApiTargets(apiTargetEnums);
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            apiManagement.setRoles(roles);
            apiManagement.setStatus(StatusEnum.ACTIVE);
            apiManagementRepository.save(apiManagement);
        }

        User checkUser = userRepository.findByUsername("admin");
        if (checkUser == null) {
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            User user = new User();
            user.setName("Admin");
            user.setUsername("admin");
            user.setEmail("admin@gmail.com");
            user.setPhone("0389718787");
            user.setPassword(pwdEnc.encode("123456"));
            user.setRoles(roles);
            user.setCreatedDate(LocalDateTime.now());
            userRepository.save(user);
        }
    }
}
