package com.example.securedjson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.securedjson.pojos.SecureGetterPoJo;
import org.junit.Test;

import javax.validation.Validation;

import static junit.framework.TestCase.assertEquals;

public class CryptoGetterTest {

    @Test
    public void encryptDefault() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));

        SecureGetterPoJo pojo = new SecureGetterPoJo();
        pojo.setCritical("Something very secure ...");

        String json = objectMapper.writeValueAsString(pojo);
        SecureGetterPoJo pojo2 = objectMapper.readValue(json, SecureGetterPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }

    @Test
    public void encryptCustomValidator() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        new EncryptionService(objectMapper, Validation.buildDefaultValidatorFactory().getValidator(), new PasswordCryptoContext("Password1"));

        SecureGetterPoJo pojo = new SecureGetterPoJo();
        pojo.setCritical("Something very secure ...");

        String json = objectMapper.writeValueAsString(pojo);
        SecureGetterPoJo pojo2 = objectMapper.readValue(json, SecureGetterPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }

}