package com.example.fintech.services.impl;

import com.example.fintech.DTOs.RequestDTO.MoMoPaymentRequestDTO;
import com.example.fintech.DTOs.ResponseDTO.MoMoPaymentResponseDTO;
import com.example.fintech.DTOs.ResponseDTO.MoMoPaymentResultDTO;
import com.example.fintech.entities.momo.MoMoPayment;
import com.example.fintech.enums.StatusEnum;
import com.example.fintech.repositories.MoMoPaymentRepository;
import com.example.fintech.services.MoMoService;
import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class MoMoServiceImpl implements MoMoService {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    MoMoPaymentRepository moMoPaymentRepository;

    @Override
    public MoMoPaymentResponseDTO createTransaction(MoMoPaymentRequestDTO requestDTO) {
        String url = "https://test-payment.momo.vn/napas/v2/controllers/main.php";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String partnerCode = "MOMO_ATM_DEV";
        String secretKey = "mD9QAVi4cm9N844jh5Y2tqjWaaJoGVFM";
        String accessKey = "w9gEg8bjA2AM2Cvr";
        String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA/JwU2qXu7InD6VV+aujmw95PHytZGD2tODBdsMBywQpdXRHmGP/QtJqWH9J1yRZ29vI/Pv8jYoczS9BXaygui5DbMb2PvqflPwWLjRFacreztzlHjLyUXog0XdpRFOL7PG9zFpYvHGo4Us0p+737HUUwDJIgc+bz3AyRNIe/zVbPReMGB3e8qAv6hJ8Tld21blQXCUBQxeF1RpAwGU6IHr5LPFSj/y15HdIxR3ZvrJYLPPZeKlotf3I7tj0RmIDeh1YjnnROiU94Ms8OTjAhq+hz+7k5rfo+pAO4cn/pfaS2Sbar+WGNQtHfA6m/uHkF/ljNW8evhmXipymQJuN7+eETPtZASBSWEsWLpfZhn2BHrYMm36isNMAa/DZ7DFykXT+md113Y2nBhN3Af0CFGpoAhQwLCcDsdk0+facwyezIuL+wcYFrH0v5Azdkng8pM+TrdlL2J7cEzDrkJEkAoVf/bRVxIGMvm0md0Hfoq6AURZc6nv8gsLL8923TcDxq2wNjJPKbqqziSA2gQVMpn/0lIqEnTyRmyvPz8jmDWcpZA3YLxWH9pEHidjRUrrec8eJCI5r3ViL7uky7YjRb9By2vzPeUjBllkKJB8lA2jaeKyWNBo8exhE30EjDB5uRTxlpiaUFTi4jdAc5Bl5F2wYe24gV4JptlSGmmGgBsJ0CAwEAAQ==";
        String orderInfo = "Thanh toán qua Ví MoMo";
        String amount = "10000";
        String token = "";
        String partnerClientId = null;
        String extraData = "";

        MoMoPaymentResultDTO moMoPaymentResultDTO = new MoMoPaymentResultDTO();
        moMoPaymentResultDTO.setPartnerCode(partnerCode);
        moMoPaymentResultDTO.setSecretKey(secretKey);
        moMoPaymentResultDTO.setAccessKey(accessKey);
        moMoPaymentResultDTO.setPublicKey(publicKey);
        moMoPaymentResultDTO.setOrderInfo(orderInfo);
        moMoPaymentResultDTO.setAmount(amount);
        moMoPaymentResultDTO.setToken(token);
        moMoPaymentResultDTO.setPartnerClientId(partnerClientId);
        moMoPaymentResultDTO.setExtraData(extraData);

        HttpEntity<MoMoPaymentResultDTO> entity = new HttpEntity<>(moMoPaymentResultDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url,
                entity, String.class);

        Gson gson = new Gson();
        MoMoPaymentResponseDTO responseDTO = gson.fromJson(response.getBody(), MoMoPaymentResponseDTO.class);

        MoMoPayment data = mapper.map(responseDTO, MoMoPayment.class);
        data.setCreatedDate(LocalDateTime.now());
        data.setCreatedUser(new ObjectId());
        data.setStatus(StatusEnum.ACTIVE);

        moMoPaymentRepository.save(data);
        return responseDTO;
    }
}
