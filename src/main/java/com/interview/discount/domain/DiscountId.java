package com.interview.discount.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public record DiscountId(UUID value) {

    public DiscountId {
        Objects.requireNonNull(value);
    }
}
