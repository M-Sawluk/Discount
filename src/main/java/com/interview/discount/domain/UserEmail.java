package com.interview.discount.domain;

import com.interview.discount.domain.exception.InvalidEmailAddressException;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record UserEmail(String userEmail)  {
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public UserEmail {
        if (!EMAIL_PATTERN.matcher(userEmail).matches()) {
            throw new InvalidEmailAddressException(userEmail);
        }
    }
}
