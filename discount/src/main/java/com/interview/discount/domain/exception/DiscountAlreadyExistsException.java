package com.interview.discount.domain.exception;

public class DiscountAlreadyExistsException extends RuntimeException {

    public DiscountAlreadyExistsException(String discountCode) {
        super("Discount with code: " + discountCode + " already exists");
    }
}
