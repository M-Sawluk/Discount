package com.interview.discount.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record DiscountCode(String code) {

    public DiscountCode(String code) {
        this.code = Objects.requireNonNull(code, "Discount code cannot be null")
                .toUpperCase();
    }
}
