package com.interview.discount.domain.exception;

import com.interview.discount.domain.CountryCode;

public class DiscountNotEntitledInUserCountryException extends RuntimeException {

    public DiscountNotEntitledInUserCountryException(CountryCode countryCode) {
        super("Discount not available for: " + countryCode.value());
    }
}
