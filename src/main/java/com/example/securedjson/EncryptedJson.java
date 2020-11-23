
package com.example.securedjson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * All fields marked as {@link Encrypt} are converted to this data structure in the JSON tree
 */
@JsonInclude(Include.NON_NULL)
public class EncryptedJson {

    @JsonProperty(value = "salt", required = true)
    @NotNull
    private byte[] salt;
    @JsonProperty(value = "iv", required = true)
    @NotNull
    private byte[] iv;
    @JsonProperty(value = "value", required = true)
    @NotNull
    private byte[] value;

    public EncryptedJson() {
    }

    public byte[] getSalt() {
        return null == this.salt ? null : Arrays.copyOf(this.salt, this.salt.length);
    }

    public void setSalt(final byte[] salt) {
        this.salt = salt != null ? Arrays.copyOf(salt, salt.length) : null;
    }

    public byte[] getIv() {
        return Arrays.copyOf(this.iv, this.iv.length);
    }

    public void setIv(final byte[] iv) {
        if (null != iv)
            this.iv = Arrays.copyOf(iv, iv.length);
        else
            this.iv = null;
    }

    public byte[] getValue() {
        return null == this.value ? null : Arrays.copyOf(this.value, this.value.length);
    }

    public void setValue(final byte[] value) {
        if (null != value)
            this.value = Arrays.copyOf(value, value.length);
        else
            this.value = null;
    }
}
