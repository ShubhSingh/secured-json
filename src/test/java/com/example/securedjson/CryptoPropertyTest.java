package com.example.securedjson;

import com.example.securedjson.pojos.SecurePropertyPoJo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.validation.Validation;

import static junit.framework.TestCase.assertEquals;

public class CryptoPropertyTest {

    @Test
    public void encryptDefault() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));

        SecurePropertyPoJo pojo = new SecurePropertyPoJo();
        pojo.setCritical("Something very secure ...");

        String json = objectMapper.writeValueAsString(pojo);
        SecurePropertyPoJo pojo2 = objectMapper.readValue(json, SecurePropertyPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }

    @Test
    public void encryptCustomValidator() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        new EncryptionService(objectMapper, Validation.buildDefaultValidatorFactory().getValidator(), new PasswordCryptoContext("Password1"));

        SecurePropertyPoJo pojo = new SecurePropertyPoJo();
        pojo.setCritical("Something very secure ...");

        String json = objectMapper.writeValueAsString(pojo);
        SecurePropertyPoJo pojo2 = objectMapper.readValue(json, SecurePropertyPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }
}