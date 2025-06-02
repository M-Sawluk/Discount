package com.interview.discount.infrastructure;

import com.interview.discount.application.IpToCountryCodeResolver;
import com.interview.discount.domain.CountryCode;
import com.interview.discount.domain.exception.NonResolvableIpException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class IpToCountryCodeResolverImpl implements IpToCountryCodeResolver {

    @Value("${country-resolver-api.url}")
    private String countryResolverUrl;

    private final RestTemplate restTemplate;

    @Override
    public CountryCode resolve(String ip) {
        CountryResolverResponse response = restTemplate.getForEntity(countryResolverUrl + "/" + ip, CountryResolverResponse.class).getBody();
        if (response == null) {
            throw new NonResolvableIpException();
        }
        return new CountryCode(response.country);
    }

    public record CountryResolverResponse(String ip, String country) { }
}
