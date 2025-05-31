package com.interview.discount.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public record DiscountUsageId(UUID value) {

    public DiscountUsageId {
        Objects.requireNonNull(value);
    }
}
