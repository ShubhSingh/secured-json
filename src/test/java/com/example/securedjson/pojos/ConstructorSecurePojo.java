package com.example.securedjson.pojos;

import com.example.securedjson.Encrypt;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConstructorSecurePojo {

    private String critical;

    public ConstructorSecurePojo(@JsonProperty @Encrypt String critical) {
        this.critical = critical;
    }

    @JsonProperty
    @Encrypt
    public String getCritical() {
        return this.critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }
}
