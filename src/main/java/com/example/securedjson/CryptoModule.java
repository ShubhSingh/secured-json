
package com.example.securedjson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ResourceBundle;

/**
 * Crypto Module for Jackson JSON library
 */
public class CryptoModule extends Module {
    public final static String GROUP_ID = "com.softwareag.sci";
    public final static String ARTIFACT_ID = "secured-json";
    //
    private final static String BUNDLE = CryptoModule.class.getPackage().getName() + ".config";
    //private final static String BUNDLE = "src/main/resources";
    //
    private final int major;
    private final int minor;
    private final int patch;
    //
    private EncryptedSerializerModifier serializerModifierModifier;
    private EncryptedDeserializerModifier deserializerModifierModifier;

    /**
     * Initialize module
     */
    public CryptoModule() {
        ResourceBundle rb = ResourceBundle.getBundle(BUNDLE);
        major = Integer.parseInt(rb.getString("projectVersionMajor"));
        minor = Integer.parseInt(rb.getString("projectVersionMinor"));
        patch = Integer.parseInt(rb.getString("projectVersionBuild"));
    }

    /**
     * Set the encryption service to use
     *
     * @param encryptionService Service to supply crypto functionality
     * @return Updated module
     */
    public CryptoModule addEncryptionService(final EncryptionService encryptionService) {
        serializerModifierModifier = new EncryptedSerializerModifier(encryptionService);
        deserializerModifierModifier = new EncryptedDeserializerModifier(encryptionService);
        return this;
    }

    /**
     * Method that returns a display that can be used by Jackson
     *
     * @return Name for display
     */
    @Override
    public String getModuleName() {
        return ARTIFACT_ID;
    }

    /**
     * Method that returns version of this module.
     *
     * @return Version info
     */
    @Override
    public Version version() {
        return new Version(major, minor, patch, null, GROUP_ID, ARTIFACT_ID);
    }

    /**
     * Method called by {@link ObjectMapper} when module is registered.
     *
     * @param context Context for 'registering extended' functionality.
     */
    @Override
    public void setupModule(final SetupContext context) {
        if ((null == serializerModifierModifier) || (null == deserializerModifierModifier))
            throw new EncryptionException("Crypto module not initialised with an encryption service");
        context.addBeanSerializerModifier(serializerModifierModifier);
        context.addBeanDeserializerModifier(deserializerModifierModifier);
    }
}

