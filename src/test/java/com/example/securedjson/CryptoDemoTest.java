package com.example.securedjson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.securedjson.pojos.SecureGetterPoJo;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CryptoDemoTest {

    @Test
    public void encryptDetailedSetupDemoWithDESAlgo() throws Exception {
        // get an object mapper
        ObjectMapper objectMapper = new ObjectMapper();
        // set up a custom crypto context - Defines teh interface to the crypto algorithms used
        ICryptoContext cryptoContext = new PasswordCryptoContext("Password", "Password",
                "DES/CBC/PKCS5Padding", "PBKDF2WithHmacSHA256", 10000, 64, "DES");
        // The encryption service holds functionality to map clear to / from encrypted JSON
        EncryptionService encryptionService = new EncryptionService(objectMapper, cryptoContext);
        // Create a Jackson module and tell it about the encryption service
        CryptoModule cryptoModule = new CryptoModule().addEncryptionService(encryptionService);
        // Tell Jackson about the new module
        objectMapper.registerModule(cryptoModule);
        //
        SecureGetterPoJo pojo = new SecureGetterPoJo();
        pojo.setCritical("The long way to set up JSON crypto ...");

        String json = objectMapper.writeValueAsString(pojo);
        SecureGetterPoJo pojo2 = objectMapper.readValue(json, SecureGetterPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }

    @Test
    public void encryptDetailedSetupDemo() throws Exception {
        // get an object mapper
        ObjectMapper objectMapper = new ObjectMapper();
        // set up a custom crypto context - Defines teh interface to the crypto algorithms used
        ICryptoContext cryptoContext = new PasswordCryptoContext("Password");
        // The encryption service holds functionality to map clear to / from encrypted JSON
        EncryptionService encryptionService = new EncryptionService(objectMapper, cryptoContext);
        // Create a Jackson module and tell it about the encryption service
        CryptoModule cryptoModule = new CryptoModule().addEncryptionService(encryptionService);
        // Tell Jackson about the new module
        objectMapper.registerModule(cryptoModule);
        //
        SecureGetterPoJo pojo = new SecureGetterPoJo();
        pojo.setCritical("The long way to set up JSON crypto ...");

        String json = objectMapper.writeValueAsString(pojo);
        SecureGetterPoJo pojo2 = objectMapper.readValue(json, SecureGetterPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }

    @Test
    public void encryptQuickSetupDemo() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        EncryptionService encryptionService = new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));
        objectMapper.registerModule(new CryptoModule().addEncryptionService(encryptionService));

        SecureGetterPoJo pojo = new SecureGetterPoJo();
        pojo.setCritical("The short way to set up JSON crypto ...");

        String json = objectMapper.writeValueAsString(pojo);
        SecureGetterPoJo pojo2 = objectMapper.readValue(json, SecureGetterPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }

    @Test
    public void encryptVeryQuickSetupDemo() throws Exception {
        ObjectMapper objectMapper = EncryptionService.getInstance(new PasswordCryptoContext("Password1"));

        SecureGetterPoJo pojo = new SecureGetterPoJo();
        pojo.setCritical("The very short way to set up JSON crypto ...");

        String json = objectMapper.writeValueAsString(pojo);
        SecureGetterPoJo pojo2 = objectMapper.readValue(json, SecureGetterPoJo.class);
        assertEquals(pojo.getCritical(), pojo2.getCritical());
    }
}