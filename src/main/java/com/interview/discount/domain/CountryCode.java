package com.interview.discount.domain;

import com.interview.discount.domain.exception.InvalidCountryCode;
import jakarta.persistence.Embeddable;

import java.util.Locale;
import java.util.Set;

@Embeddable
public record CountryCode(String value) {

    public CountryCode {
        if (!Set.of(Locale.getISOCountries()).contains(value)) {
            throw new InvalidCountryCode(value);
        }
    }
}
