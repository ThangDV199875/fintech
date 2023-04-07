package com.example.fintech.authentications;

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
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

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

    private static final Logger loggers = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String requestURI = request.getRequestURI();
        System.out.println("requestURI: "+requestURI);
        if (!requestURI.contains("/access-permission") &&
                !requestURI.contains("/api-management") &&
                !requestURI.contains("/payment/momo/create-transaction") &&
                !requestURI.contains("/role-management") &&
                !requestURI.contains("/v3") &&
                !requestURI.contains("/swagger-ui")) {
            ApiManagement checkAPI = apiManagementRepository.findByName(requestURI);
            if (checkAPI == null) {
                System.out.println("Request uri does not exist in the system!");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request uri does not exist in the system!");
            }
        }

        VALIDATETOKEN: try {
            String jwt = getJwtFromRequest(request);

            if (!StringUtils.hasText(jwt))
                break VALIDATETOKEN;

            UserDetails userDetails = tokenProvider.getUserPrincipalFromJWT(jwt);

            for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
//                if (RoleEnum.OAUTH.name().equals(grantedAuthority.getAuthority())) {
//                    String requestURI = request.getRequestURI();
//                    if (!requestURI.contains("/user/verify"))
//                        break VALIDATETOKEN;
//                }

            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception ex) {
            loggers.error("Could not set user authentication in security context", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
