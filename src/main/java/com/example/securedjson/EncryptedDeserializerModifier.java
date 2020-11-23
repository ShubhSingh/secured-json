
package com.example.securedjson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

import java.util.Iterator;

/**
 * Class that defines objects that to be used to participate in constructing {@link JsonDeserializer} instances (via {@link DeserializerFactory}).
 */
public class EncryptedDeserializerModifier extends BeanDeserializerModifier {

    private final EncryptionService encryptionService;

    public EncryptedDeserializerModifier(final EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Add deserialization functionality for {@link Encrypt} marked fields
     */
    @Override
    public BeanDeserializerBuilder updateBuilder(final DeserializationConfig config, final BeanDescription beanDescription, final BeanDeserializerBuilder builder) {
        Iterator it = builder.getProperties();

        while (it.hasNext()) {
            SettableBeanProperty p = (SettableBeanProperty) it.next();
            if (null != p.getAnnotation(Encrypt.class)) {
                JsonDeserializer<Object> current = p.getValueDeserializer();
                builder.addOrReplaceProperty(p.withValueDeserializer(new EncryptedJsonDeserializer(encryptionService, current)), true);
            }
        }
        return builder;
    }
}
