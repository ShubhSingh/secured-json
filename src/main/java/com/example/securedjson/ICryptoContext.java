package com.example.securedjson;

/**
 * Interface defining required core crypto functionality
 */
interface ICryptoContext {

    /**
     * Decrypt an encrypted JSON object. Contains salt and iv as fields
     *
     * @param value JSON data
     * @return Decrypted byte array
     */
    byte[] decrypt(EncryptedJson value);

    /**
     * Encrypted a string as a byte array and encode using base 64
     *
     * @param source Byte array to be encrypted
     * @return Encrypted data
     */
    byte[] encrypt(byte[] source);

    /**
     * Get the initialization vector
     *
     * @return Vector as byte array
     */
    byte[] getIv();

    /**
     * Get the salt
     *
     * @return Salt as byte array
     */
    byte[] getSalt();

}
