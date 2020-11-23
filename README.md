
# Secured Json

It's a SCI module to support JSON encryption/decryption

## Build

Windows or Linux
```
mvn clean install
```
## How to use

These examples are demonstrated in the ```CryptoDemo``` unit test class

### Option 1 - Very Quick and Easy

Add the crypto module to your project. Common use case with a cipher of AES/CBC/PKCS5Padding and a key factory algorithm of PBKDF2WithHmacSHA512
Just supply a password.

```java
ObjectMapper objectMapper = EncryptionService.getInstance(new PasswordCryptoContext("Password1"));
```


### Option 2 - Quick and Easy

Similar to Option 1, but used when you already have a ObjectMapper 

```java
ObjectMapper objectMapper = ...
EncryptionService encryptionService = new EncryptionService(objectMapper, new PasswordCryptoContext("Password1"));
objectMapper.registerModule(new CryptoModule().addEncryptionService(encryptionService));
```


### Option 3 - Configure Everything

Where you just need full control. 

```java
private static final String ALGORITHM_TYPE = "DES";
private static final int KEY_LENGTH = 64;
private static final int ITERATION_COUNT = 10000;
private static final String CIPHER_NAME = "DES/CBC/NoPadding";
private static final String KEY_NAME = "PBKDF2WithHmacSHA256";

// get an object mapper
ObjectMapper objectMapper = new ObjectMapper();
// set up a custom crypto context - Defines teh interface to the crypto algorithms used
ICryptoContext cryptoContext = new PasswordCryptoContext(encryptionSecret, encryptionSecret, CIPHER_NAME, KEY_NAME, ITERATION_COUNT, KEY_LENGTH, ALGORITHM_TYPE);
// The encryption service holds functionality to map clear to / from encrypted JSON
EncryptionService encryptionService = new EncryptionService(objectMapper, cryptoContext);
// Create a Jackson module and tell it about the encryption service
CryptoModule cryptoModule = new CryptoModule().addEncryptionService(encryptionService);
 // Tell Jackson about the new module
objectMapper.registerModule(cryptoModule);
```


### Encrypt a field

Any field that is required to be encrypted has to be marked as such.  This can be done by either annotating the getter() or 
by annotating the field definition.

This ...

```java
    private String critical;
    ...
    @JsonProperty
    @Encrypt
    public String getCritical() {
        return this.critical;
    }
```

... or ...

```java
    @JsonProperty
    @Encrypt
    private String critical;
```

## Output JSON Format
```json
{  
   "critical":{  
      "salt":"IRqsz99no75sx9SCGrzOSEdoMVw=",
      "iv":"bfKvxBhq7X5su9VtvDdOGQ==",
      "value":"pXWsFPzCnmPieitbGfkvofeQE3fj0Kb4mSP7e28+Jc0="
   }
}
```

## Java Cryptography Extension (JCE) Unlimited Strength

As shipped, Java has limitations on the strength of encryption allowable.  To allow full strength encryption to be used, you will need to
enable the Unlimited Strength Jurisdiction Policy.

[Policy Files](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)

Problems with encyption strength will usually show as an error in the following form.
```
EncryptionException: java.security.InvalidKeyException: 
   Illegal key size or default parameters
```

## Include Using Maven
```
<dependency>
  <groupId>com.example</groupId>
  <artifactId>secured-json</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Include Using Gradle

```
compile group: 'com.example', name: 'secured-json', version: '1.0.0'
```

