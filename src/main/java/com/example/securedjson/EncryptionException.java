
package com.example.securedjson;

/**
 * General exception used when any errant event occurs during encrypt/decrypt operations
 */
public class EncryptionException extends RuntimeException {

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(Throwable cause) {
        super(cause);
    }
}
