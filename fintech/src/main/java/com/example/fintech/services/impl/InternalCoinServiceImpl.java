package com.example.fintech.services.impl;

import com.example.fintech.DTOs.RequestDTO.InternalCoinRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.InternalCoinInfoDTO;
import com.example.fintech.DTOs.ResponseDTO.InternalCoinListDTO;
import com.example.fintech.authentications.UserPrincipal;
import com.example.fintech.entities.Partner;
import com.example.fintech.entities.User;
import com.example.fintech.entities.internal_payment.InternalCoin;
import com.example.fintech.enums.StatusEnum;
import com.example.fintech.repositories.InternalCoinRepository;
import com.example.fintech.repositories.PartnerRepository;
import com.example.fintech.repositories.UserRepository;
import com.example.fintech.services.InternalCoinService;
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
public class InternalCoinServiceImpl implements InternalCoinService {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InternalCoinRepository internalCoinRepository;

    @Autowired
    PartnerRepository partnerRepository;

    @Override
    public void create(InternalCoinRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        User checkUser = userRepository.findUserById(new ObjectId(dto.getUserId()));
        if (checkUser == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist in the system!");

        Partner checkPartner = partnerRepository.findPartnerById(new ObjectId(dto.getPartnerId()));
        if (checkPartner == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Partner does not exist in the system!");

        InternalCoin checkInterCoin = internalCoinRepository.findByUserId(new ObjectId(dto.getUserId()));
        if (checkInterCoin != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Internal coin of this user exists in the system!");

        InternalCoin internalCoin = mapper.map(dto, InternalCoin.class);
        internalCoin.setCreatedDate(LocalDateTime.now());
        internalCoin.setCreatedUser(user.getId());
        internalCoin.setStatus(StatusEnum.ACTIVE);
        internalCoinRepository.save(internalCoin);
    }

    @Override
    public void update(String id, InternalCoinRequestDTO dto, UserPrincipal currentUser) {
        User user = userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        User checkUser = userRepository.findUserById(new ObjectId(dto.getUserId()));
        if (checkUser == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist in the system!");

        Partner checkPartner = partnerRepository.findPartnerById(new ObjectId(dto.getPartnerId()));
        if (checkPartner == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Partner does not exist in the system!");

        InternalCoin internalCoin = internalCoinRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Interal coin of this user does not exist in the system!"));

        internalCoin.setUpdatedDate(LocalDateTime.now());
        internalCoin.setUpdatedUser(user.getId());
        internalCoinRepository.save(internalCoin);
    }

    @Override
    public void delete(String id, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        InternalCoin internalCoin = internalCoinRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Internal coin of this user does not exist in the system!"));

        internalCoinRepository.delete(internalCoin);
    }

    @Override
    public InternalCoinInfoDTO info(String id, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        InternalCoin internalCoin = internalCoinRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Internal coin of this user does not exist in the system!"));

        User user = userRepository.findUserById(internalCoin.getUserId());

        InternalCoinInfoDTO internalCoinInfoDTO = mapper.map(internalCoin, InternalCoinInfoDTO.class);
        internalCoinInfoDTO.setUserName(user.getUsername());
        if (internalCoin.getCreatedUser() == null) {
            internalCoinInfoDTO.setCreatedUser("");
        } else {
            User createdUser = userRepository.findUserById(internalCoin.getCreatedUser());
            internalCoinInfoDTO.setCreatedUser(createdUser.getUsername());
        }

        if (internalCoin.getUpdatedUser() == null) {
            internalCoinInfoDTO.setUpdatedUser("");
        } else {
            User updatedUser = userRepository.findUserById(internalCoin.getUpdatedUser());
            internalCoinInfoDTO.setUpdatedUser(updatedUser.getUsername());
        }
        return internalCoinInfoDTO;
    }

    @Override
    public InternalCoinListDTO list(String userName, String partnerName, Integer page, Integer pageSize, UserPrincipal currentUser) {
        userRepository.findById(new ObjectId(currentUser.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdDate");
        List<InternalCoinInfoDTO> internalCoinInfoDTOList = new ArrayList<>();
        int totalPage = 0;
        long totalSize = 0;
        if (userName == null && partnerName == null) {
            Page<InternalCoin> internalCoinPage = internalCoinRepository.findAll(pageable);
            for (InternalCoin internalCoin : internalCoinPage) {
                InternalCoinInfoDTO internalCoinInfoDTO = mapper.map(internalCoin, InternalCoinInfoDTO.class);
                User user = userRepository.findUserById(internalCoin.getUserId());
                internalCoinInfoDTO.setUserName(user.getUsername());
                if (internalCoin.getCreatedUser() == null) {
                    internalCoinInfoDTO.setCreatedUser("");
                } else {
                    User createdUser = userRepository.findUserById(internalCoin.getCreatedUser());
                    internalCoinInfoDTO.setCreatedUser(createdUser.getUsername());
                }

                if (internalCoin.getUpdatedUser() == null) {
                    internalCoinInfoDTO.setUpdatedUser("");
                } else {
                    User updatedUser = userRepository.findUserById(internalCoin.getUpdatedUser());
                    internalCoinInfoDTO.setUpdatedUser(updatedUser.getUsername());
                }
                internalCoinInfoDTOList.add(internalCoinInfoDTO);
            }
            totalPage = internalCoinPage.getTotalPages();
            totalSize = internalCoinPage.getTotalElements();
        } else {
            User checkUser = userRepository.findByUsername(userName.trim());
            if (checkUser == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist in the system!");

            Partner checkPartner = partnerRepository.findByNameInIgnoreCase(partnerName.trim());
            if (checkPartner == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Partner does not exist in the system!");

            Page<InternalCoin> internalCoinPage = internalCoinRepository.findByUserIdOrPartnerId(checkUser.getId(), checkPartner.getId(), pageable);
            for (InternalCoin internalCoin : internalCoinPage) {
                InternalCoinInfoDTO internalCoinInfoDTO = mapper.map(internalCoin, InternalCoinInfoDTO.class);
                User user = userRepository.findUserById(internalCoin.getUserId());
                internalCoinInfoDTO.setUserName(user.getUsername());
                if (internalCoin.getCreatedUser() == null) {
                    internalCoinInfoDTO.setCreatedUser("");
                } else {
                    User createdUser = userRepository.findUserById(internalCoin.getCreatedUser());
                    internalCoinInfoDTO.setCreatedUser(createdUser.getUsername());
                }

                if (internalCoin.getUpdatedUser() == null) {
                    internalCoinInfoDTO.setUpdatedUser("");
                } else {
                    User updatedUser = userRepository.findUserById(internalCoin.getUpdatedUser());
                    internalCoinInfoDTO.setUpdatedUser(updatedUser.getUsername());
                }
                internalCoinInfoDTOList.add(internalCoinInfoDTO);
            }
            totalPage = internalCoinPage.getTotalPages();
            totalSize = internalCoinPage.getTotalElements();
        }

        InternalCoinListDTO internalCoinListDTO = new InternalCoinListDTO();
        internalCoinListDTO.setInternalCoinInfoDTOS(internalCoinInfoDTOList);
        internalCoinListDTO.setTotalPage(totalPage);
        internalCoinListDTO.setTotalSize(totalSize);
        return internalCoinListDTO;
    }
}
