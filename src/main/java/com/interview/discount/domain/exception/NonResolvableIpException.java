package com.interview.discount.domain.exception;

public class NonResolvableIpException extends RuntimeException {
    public NonResolvableIpException() {
        super("Unable to resolve IP address");
    }
}
