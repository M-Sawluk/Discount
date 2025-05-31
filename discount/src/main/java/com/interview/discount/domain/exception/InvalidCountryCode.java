package com.interview.discount.domain.exception;

public class InvalidCountryCode extends RuntimeException {

    public InvalidCountryCode(String countryCode) {
        super("Invalid code code: " + countryCode);
    }
}
