package com.interview.discount.domain.exception;

public class InvalidEmailAddressException extends RuntimeException {

    public InvalidEmailAddressException(String value) {
        super("Email address: " + value + " is invalid");
    }
}
