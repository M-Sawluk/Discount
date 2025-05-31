package com.interview.discount.domain.exception;

public class DiscountAlreadyUsed extends RuntimeException {
    public DiscountAlreadyUsed(String message) {
        super(message);
    }
}
