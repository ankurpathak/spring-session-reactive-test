package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@JsonInclude(Include.NON_EMPTY)
@Document(collection = Documents.TOKEN)
public class Token extends Domain<String> implements Serializable {

    @Indexed(name = Documents.Index.TOKEN_VALUE_IDX, unique = true, sparse = true)
    private String value;

    @Indexed(name = Documents.Index.TOKEN_EXPIRY_IDX, expireAfterSeconds= EXPIRATION_IN_SECONDS)
    private Instant expiry;
    private static final int EXPIRATION_IN_MINUTES = 30;

    private static final int EXPIRATION_IN_SECONDS = 30 * 60;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }

    private final Instant calculateExpiryDate(int expirationInMinutes) {
        return Instant.now().plus(expirationInMinutes,ChronoUnit.MINUTES);
    }


    public  static Token getInstance(){
        return new Token();
    }

    public Token updateToken(String token) {
        this.value = token;
        this.expiry = this.calculateExpiryDate(EXPIRATION_IN_MINUTES);
        return this;
    }


    public Token() {
        this.value = RandomStringUtils.randomAlphanumeric(8, 8).toLowerCase();
        this.expiry = this.calculateExpiryDate(EXPIRATION_IN_MINUTES);
    }

    @Override
    public String resourcePath() {
        return Documents.TOKEN;
    }


    public enum TokenStatus {
        VALID,
        INVALID,
        EXPIRED
    }


}
