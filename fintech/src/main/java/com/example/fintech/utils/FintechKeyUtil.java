package com.example.fintech.utils;

import com.example.fintech.DTOs.ResponseDTO.FintechKeyResponseDTO;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class FintechKeyUtil {

    public static FintechKeyResponseDTO generateKey(String partnerCode) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        random.nextBytes(partnerCode.getBytes(StandardCharsets.UTF_8));
        keyPairGenerator.initialize(2048, random);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        Base64.Encoder encoder = Base64.getEncoder();

        FintechKeyResponseDTO responseDTO = new FintechKeyResponseDTO();
        responseDTO.setAccessKey(encoder.encodeToString(publicKey.getEncoded()));
        responseDTO.setSecretKey(encoder.encodeToString(publicKey.getEncoded()));

        return responseDTO;
    }

}
