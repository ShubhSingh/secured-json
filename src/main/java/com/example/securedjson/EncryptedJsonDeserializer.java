
package com.example.securedjson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

/**
 * Implementation of  {@link JsonDeserializer} to supply a callback
 * that can be used to create contextual (context-dependent) instances of
 * deserializer - Useful for annotation handling
 */
public class EncryptedJsonDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {
    private final EncryptionService service;
    private final JsonDeserializer<Object> baseDeserializer;
    private final BeanProperty property;

    public EncryptedJsonDeserializer(final EncryptionService service, final JsonDeserializer<Object> baseDeserializer) {
        this.service = service;
        this.baseDeserializer = baseDeserializer;
        this.property = null;
    }

    public EncryptedJsonDeserializer(final EncryptionService service, final JsonDeserializer<Object> wrapped, final BeanProperty property) {
        this.service = service;
        this.baseDeserializer = wrapped;
        this.property = property;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Encrypted field deserializer
     */
    @Override
    public Object deserialize(final JsonParser parser, final DeserializationContext context) throws JsonMappingException {
        JsonDeserializer<?> deserializer = baseDeserializer;
        if (deserializer instanceof ContextualDeserializer) {
            deserializer = ((ContextualDeserializer) deserializer).createContextual(context, property);
        }
        return service.decrypt(parser, deserializer, context, property != null ? property.getType() : null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext context, final BeanProperty property) {
        return new EncryptedJsonDeserializer(service, baseDeserializer, property);
    }
}
