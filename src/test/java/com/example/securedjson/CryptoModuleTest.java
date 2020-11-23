package com.example.securedjson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class CryptoModuleTest {

    private CryptoModule cryptoModule;

    @Before
    public void setup() {
        cryptoModule = new CryptoModule();
    }

    @Test
    public void addEncryptionService() {
        EncryptionService encryptionService = new EncryptionService(new ObjectMapper(), new PasswordCryptoContext("password"));
        cryptoModule = new CryptoModule();
        assertTrue(cryptoModule == cryptoModule.addEncryptionService(encryptionService));
    }

    @Test
    public void getModuleName() {
        assertEquals(CryptoModule.ARTIFACT_ID, cryptoModule.getModuleName());
    }

    @Test
    public void version() {
        cryptoModule = new CryptoModule();
        Version version = cryptoModule.version();
        assertEquals("1.0.0", version.toString());
        assertEquals(CryptoModule.ARTIFACT_ID, version.getArtifactId());
        assertEquals(CryptoModule.GROUP_ID, version.getGroupId());
    }

    @Test(expected = EncryptionException.class)
    public void setupModuleFail() {
        Module.SetupContext context = EasyMock.createMock(Module.SetupContext.class);
        replay();
        cryptoModule.setupModule(context);
    }

    @Test
    public void setupModule() {
        Module.SetupContext context = EasyMock.createMock(Module.SetupContext.class);
        context.addBeanDeserializerModifier(isA(BeanDeserializerModifier.class));
        expectLastCall().times(1);
        context.addBeanSerializerModifier(isA(BeanSerializerModifier.class));
        expectLastCall().times(1);
        replay();
        //
        EncryptionService encryptionService = new EncryptionService(new ObjectMapper(), new PasswordCryptoContext("password"));
        cryptoModule.addEncryptionService(encryptionService);
        cryptoModule.setupModule(context);
        //
        verify();
    }
}