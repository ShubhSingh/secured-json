
package com.example.securedjson;

/**
 * Default crypto context for handling password based encryption processes
 */
public class PasswordCryptoContext extends BaseCryptoContext {
    public static final String CIPHER_NAME = "AES/CBC/PKCS5Padding";
    public static final String KEY_NAME = "PBKDF2WithHmacSHA512";
    public static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * Flexible constructor allowing customization of all parameters in the context
     *
     * @param readPassword  Password for decrypting fields
     * @param writePassword Password for encrypting fields
     * @param cipherName    Cipher to be employed, e.g. AES/CBC/PKCS5Padding
     * @param keyName       Key generator to be employed, e.g. PBKDF2WithHmacSHA512
     * @param iterationCount the iteration count e.g. 5000
     * @param keyLength the to-be-derived key length e.g. 64
     * @param algorithmType Name of alogorithm type e.g. DES
     * @throws EncryptionException Thrown if unable to make context
     */
    public PasswordCryptoContext(final String readPassword, final String writePassword, final String cipherName,
                                 final String keyName, int iterationCount, int keyLength,
                                 final String algorithmType) throws EncryptionException {
        super(readPassword, writePassword, cipherName, keyName, iterationCount, keyLength, algorithmType);
        if ((readPassword.length() < MIN_PASSWORD_LENGTH) || (writePassword.length() < MIN_PASSWORD_LENGTH))
            throw new EncryptionException("Minimum password length is " + MIN_PASSWORD_LENGTH + " characters");
    }

    /**
     * Flexible constructor allowing customization of all parameters in the context
     *
     * @param readPassword  Password for decrypting fields
     * @param writePassword Password for encrypting fields
     * @param cipherName    Cipher to be employed, e.g. AES/CBC/PKCS5Padding
     * @param keyName       Key generator to be employed, e.g. PBKDF2WithHmacSHA512
     * @throws EncryptionException Thrown if unable to make context
     */
    public PasswordCryptoContext(final String readPassword, final String writePassword, final String cipherName, final String keyName) throws EncryptionException {
        super(readPassword, writePassword, cipherName, keyName);
        if ((readPassword.length() < MIN_PASSWORD_LENGTH) || (writePassword.length() < MIN_PASSWORD_LENGTH))
            throw new EncryptionException("Minimum password length is " + MIN_PASSWORD_LENGTH + " characters");
    }

    public PasswordCryptoContext(final String readPassword, final String writePassword) throws EncryptionException {
        this(readPassword, writePassword, CIPHER_NAME, KEY_NAME);
    }

    public PasswordCryptoContext(final String password) throws EncryptionException {
        this(password, password);
    }
}

