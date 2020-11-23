package com.example.securedjson;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class PasswordCryptoContextTest {

    @Test
    public void testConstructorGood() {
        String password = "password1";
        assertTrue(password.length() >= PasswordCryptoContext.MIN_PASSWORD_LENGTH);
        new PasswordCryptoContext(password, password, PasswordCryptoContext.CIPHER_NAME, PasswordCryptoContext.KEY_NAME);
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorShort() {
        String password = "short";
        assertTrue(password.length() < PasswordCryptoContext.MIN_PASSWORD_LENGTH);
        new PasswordCryptoContext(password);
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorNullCipherName() {
        new PasswordCryptoContext("password1", "password1", null, PasswordCryptoContext.KEY_NAME);
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorNullKeyName() {
        new PasswordCryptoContext("password1", "password1", PasswordCryptoContext.CIPHER_NAME, null);
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorUnknownCipher() {
        new PasswordCryptoContext("password1", "password1", "UnknownCipher", PasswordCryptoContext.KEY_NAME);
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorLowIterationCount() {
        new PasswordCryptoContext("password1", "password1", PasswordCryptoContext.CIPHER_NAME, PasswordCryptoContext.KEY_NAME, 2000, 64, "DES");
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorNullAlgorithmType() {
        new PasswordCryptoContext("password1", "password1", PasswordCryptoContext.CIPHER_NAME, PasswordCryptoContext.KEY_NAME, 8000, 64, null);
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorWrongKeyLengthForDES() {
        new PasswordCryptoContext("password1", "password1", PasswordCryptoContext.CIPHER_NAME, PasswordCryptoContext.KEY_NAME, 8000, 128, "DES");
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorWrongKeyLengthForAES() {
        new PasswordCryptoContext("password1", "password1", PasswordCryptoContext.CIPHER_NAME, PasswordCryptoContext.KEY_NAME, 8000, 64, "AES");
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorUnknownAlgorithmType() {
        new PasswordCryptoContext("password1", "password1", PasswordCryptoContext.CIPHER_NAME, PasswordCryptoContext.KEY_NAME, 8000, 64, "UnknownAlgorithm");
    }

    @Test(expected = EncryptionException.class)
    public void testConstructorUnknownKey() {
        new PasswordCryptoContext("password1", "password1", PasswordCryptoContext.CIPHER_NAME, "UnknownKey");
    }

    @Test(expected = EncryptionException.class)
    public void testConstructor1() {
        new PasswordCryptoContext(null);
    }

    @Test(expected = EncryptionException.class)
    public void testConstructor2() {
        new PasswordCryptoContext(null, "password1");
    }

    @Test(expected = EncryptionException.class)
    public void testConstructor3() {
        new PasswordCryptoContext("password1", null);
    }
}