package com.ankurpathak.springsessionreactivetest;


import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.math.BigInteger;

public abstract class ExtendedDomain<ID extends Serializable> extends Domain<ID> {

    @CreatedBy
    @JsonView({View.Public.class, View.Me.class})
    private BigInteger createdBy;

    @LastModifiedBy
    @JsonView({View.Public.class, View.Me.class})
    private BigInteger updatedBy;


    public ExtendedDomain() {
            createdBy = updatedBy = User.ANONYMOUS_ID;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public BigInteger getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(BigInteger updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ExtendedDomain createdBy(BigInteger createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public ExtendedDomain updatedBy(BigInteger updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }
}
