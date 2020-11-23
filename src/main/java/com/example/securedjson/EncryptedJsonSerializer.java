
package com.example.securedjson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Implementation of API used by {@link ObjectMapper}  for {@link JsonSerializer}s too) to serialize required objects
 */
public class EncryptedJsonSerializer extends JsonSerializer<Object> {
    private final JsonSerializer<Object> baseSerializer;
    private final EncryptionService encryptionService;

    public EncryptedJsonSerializer(final EncryptionService encryptionService, final JsonSerializer<Object> baseSerializer) {
        this.encryptionService = encryptionService;
        this.baseSerializer = baseSerializer;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Encrypted field serializer
     */
    @Override
    public void serialize(final Object object, final JsonGenerator generator, final SerializerProvider provider) throws IOException, EncryptionException {
        StringWriter writer = new StringWriter();
        JsonGenerator nestedGenerator = generator.getCodec().getFactory().createGenerator(writer);
        if (null == baseSerializer)
            provider.defaultSerializeValue(object, nestedGenerator);
        else
            baseSerializer.serialize(object, nestedGenerator, provider);
        nestedGenerator.close();
        EncryptedJson encrypted = encryptionService.encrypt(writer.getBuffer().toString(), "UTF-8");
        generator.writeObject(encrypted);
    }
}
