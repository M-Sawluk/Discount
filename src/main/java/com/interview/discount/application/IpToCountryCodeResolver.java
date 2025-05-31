package com.interview.discount.application;

import com.interview.discount.domain.CountryCode;

public interface IpToCountryCodeResolver {

    CountryCode resolve(String ip);
}
