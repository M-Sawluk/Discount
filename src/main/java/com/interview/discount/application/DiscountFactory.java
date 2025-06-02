package com.interview.discount.application;

import com.interview.discount.domain.CountryCode;
import com.interview.discount.domain.Discount;
import com.interview.discount.domain.DiscountCode;
import com.interview.discount.domain.DiscountUsageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
@RequiredArgsConstructor
public class DiscountFactory {

    private final Clock clock;

    public Discount create(DiscountCreationRequest request) {
        return new Discount(
                new DiscountCode(request.code()),
                request.maxUsageCount(),
                new CountryCode(request.entitledCountry()),
                DiscountUsageType.valueOf(request.usageType()),
                clock
        );
    }
}
