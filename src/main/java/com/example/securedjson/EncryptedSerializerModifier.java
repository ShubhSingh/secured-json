
package com.example.securedjson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation that defines API for objects that can be registered (for {@link BeanSerializerFactory}
 * to participate in constructing {@link BeanSerializer} instances.
 */
public class EncryptedSerializerModifier extends BeanSerializerModifier {
    private final EncryptionService encryptionService;

    /**
     * Constructor
     *
     * @param encryptionService Encryption services to use to handle {@link Encrypt} marked fields
     */
    public EncryptedSerializerModifier(final EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Add serialization functionality for {@link Encrypt} marked fields
     */
    @Override
    public List<BeanPropertyWriter> changeProperties(final SerializationConfig config, final BeanDescription beanDescription, final List<BeanPropertyWriter> beanProperties) {
        List<BeanPropertyWriter> newWriters = new ArrayList<>();

        for (final BeanPropertyWriter writer : beanProperties) {
            if (null == writer.getAnnotation(Encrypt.class)) {
                newWriters.add(writer);
            } else {
                try {
                    JsonSerializer<Object> encryptSer = new EncryptedJsonSerializer(encryptionService, writer.getSerializer());
                    newWriters.add(new EncryptedPropertyWriter(writer, encryptSer));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return newWriters;
    }

    static class EncryptedPropertyWriter extends BeanPropertyWriter {
        EncryptedPropertyWriter(final BeanPropertyWriter base, final JsonSerializer<Object> deserializer) {
            super(base);
            this._serializer = deserializer;
        }
    }
}
