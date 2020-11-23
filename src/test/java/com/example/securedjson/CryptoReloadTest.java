package com.example.securedjson;

import com.example.securedjson.pojos.SecurePropertyPoJo;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CryptoReloadTest {

    private final static String TEST_JSON = "{\"critical\":{\"salt\":\"IRqsz99no75sx9SCGrzOSEdoMVw=\",\"iv\":\"bfKvxBhq7X5su9VtvDdOGQ==\"," +
            "\"value\":\"pXWsFPzCnmPieitbGfkvofeQE3fj0Kb4mSP7e28+Jc0=\"}}";

    @Test
    public void encryptReload() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        EncryptionService encryptionService = new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));
        objectMapper.registerModule(new CryptoModule().addEncryptionService(encryptionService));

        SecurePropertyPoJo pojo = new SecurePropertyPoJo();
        pojo.setCritical("Something very secure ...");

        SecurePropertyPoJo pojo2 = objectMapper.readValue(TEST_JSON, SecurePropertyPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }

    @Test(expected = JsonMappingException.class)
    public void encryptReloadChangePasswordsThrowException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        EncryptionService encryptionService = new EncryptionService(objectMapper, new PasswordCryptoContext("Password1", "Password2"));
        objectMapper.registerModule(new CryptoModule().addEncryptionService(encryptionService));

        SecurePropertyPoJo pojo = new SecurePropertyPoJo();
        pojo.setCritical("Something very secure ...");

        SecurePropertyPoJo pojo2 = objectMapper.readValue(TEST_JSON, SecurePropertyPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());

        String json = objectMapper.writeValueAsString(pojo2);
        objectMapper.readValue(json, SecurePropertyPoJo.class);
    }

    @Test
    public void encryptReloadChangePasswordsSecondService() throws Exception {
        ObjectMapper objectMapper1 = new ObjectMapper();
        EncryptionService encryptionService1 = new EncryptionService(objectMapper1, new PasswordCryptoContext("Password1", "Password2"));
        objectMapper1.registerModule(new CryptoModule().addEncryptionService(encryptionService1));

        ObjectMapper objectMapper2 = new ObjectMapper();
        EncryptionService encryptionService2 = new EncryptionService(objectMapper2, new PasswordCryptoContext("Password2"));
        objectMapper2.registerModule(new CryptoModule().addEncryptionService(encryptionService2));

        SecurePropertyPoJo pojo = new SecurePropertyPoJo();
        pojo.setCritical("Something very secure ...");

        SecurePropertyPoJo pojo2 = objectMapper1.readValue(TEST_JSON, SecurePropertyPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());

        String json = objectMapper1.writeValueAsString(pojo2);
        SecurePropertyPoJo pojo3 = objectMapper2.readValue(json, SecurePropertyPoJo.class);
        assertEquals(pojo2.getCritical(), pojo3.getCritical());
    }

}