package com.example.securedjson;

import com.example.securedjson.pojos.SecurePropertyPoJo;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class ValidationTest {

    private final static String TEST_JSON_NULL_SALT = "{\"critical\":{\"salt\":null,\"iv\":\"bfKvxBhq7X5su9VtvDdOGQ==\"," +
            "\"value\":\"pXWsFPzCnmPieitbGfkvofeQE3fj0Kb4mSP7e28+Jc0=\"}}";
    private final static String TEST_JSON_NULL_IV = "{\"critical\":{\"salt\":\"IRqsz99no75sx9SCGrzOSEdoMVw=\",\"iv\":null," +
            "\"value\":\"pXWsFPzCnmPieitbGfkvofeQE3fj0Kb4mSP7e28+Jc0=\"}}";
    private final static String TEST_JSON_NULL_VALUE = "{\"critical\":{\"salt\":\"IRqsz99no75sx9SCGrzOSEdoMVw=\",\"iv\":\"bfKvxBhq7X5su9VtvDdOGQ==\"," + "\"value\":null}}";
    private final static String TEST_JSON_MULTIPLE_NULLS = "{\"critical\":{\"salt\":null,\"iv\":null," + "\"value\":null}}";

    @Test(expected = JsonMappingException.class)
    public void nullSaltValidatorTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));
        objectMapper.readValue(TEST_JSON_NULL_SALT, SecurePropertyPoJo.class);
    }

    @Test(expected = JsonMappingException.class)
    public void nullIVValidatorTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));
        objectMapper.readValue(TEST_JSON_NULL_IV, SecurePropertyPoJo.class);
    }

    @Test(expected = JsonMappingException.class)
    public void nullValueValidatorTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));
        objectMapper.readValue(TEST_JSON_NULL_VALUE, SecurePropertyPoJo.class);
    }

    @Test(expected = JsonMappingException.class)
    public void multipleErrorsValidatorTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));
        objectMapper.readValue(TEST_JSON_MULTIPLE_NULLS, SecurePropertyPoJo.class);
    }
}