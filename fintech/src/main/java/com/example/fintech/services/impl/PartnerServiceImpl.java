package com.example.fintech.services.impl;

import com.example.fintech.DTOs.RequestDTO.PartnerRequestDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.entities.Partner;
import com.example.fintech.entities.User;
import com.example.fintech.enums.StatusEnum;
import com.example.fintech.repositories.PartnerRepository;
import com.example.fintech.repositories.UserRepository;
import com.example.fintech.services.PartnerService;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void create(PartnerRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Partner partner = mapper.map(dto, Partner.class);

        //Create access key
        String accessKey = dto.getPartnerCode() + "|" + dto.getEmail();
        String accessKeyEncode = DigestUtils.sha256Hex(accessKey);

        //Create secret keyy
        String secretKey = dto.getPartnerCode() + "|" + dto.getEmail() + "|" + dto.getPhone();
        String secretKeyEncode = DigestUtils.sha256Hex(secretKey);

        //Set data
        partner.setAccessKey(accessKeyEncode);
        partner.setSecretKey(secretKeyEncode);
        partner.setStatus(StatusEnum.ACTIVE);
        partner.setCreatedDate(LocalDateTime.now());
        partner.setCreatedUser(user.getId());

        partnerRepository.save(partner);
    }
}
