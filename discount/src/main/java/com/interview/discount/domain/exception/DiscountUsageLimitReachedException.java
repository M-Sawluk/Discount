package com.interview.discount.domain.exception;

public class DiscountUsageLimitReachedException extends RuntimeException {

    public DiscountUsageLimitReachedException() {
        super("Discount limit reached");
    }
}
