package com.example.securedjson.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.securedjson.Encrypt;

public class SecurePropertyPoJo {

    @JsonProperty
    @Encrypt
    private String critical;

    public String getCritical() {
        return this.critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }
}
