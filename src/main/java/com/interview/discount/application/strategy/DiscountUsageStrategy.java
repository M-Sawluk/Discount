package com.interview.discount.application.strategy;

import com.interview.discount.domain.CountryCode;
import com.interview.discount.domain.Discount;
import com.interview.discount.domain.DiscountUsage;
import com.interview.discount.domain.DiscountUsageId;
import com.interview.discount.domain.DiscountUsageRepository;
import com.interview.discount.domain.UserEmail;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class DiscountUsageStrategy {

    protected final DiscountUsageRepository discountUsageRepository;

    abstract DiscountUsageId use(Discount discount, UserEmail userEmail, CountryCode userCountry);

    protected DiscountUsageId createUsage(Discount discount, UserEmail userEmail, CountryCode userCountry) {
        discount.useDiscount(userCountry);
        DiscountUsage discountUsage = new DiscountUsage(discount, userEmail);
        discountUsageRepository.save(discountUsage);
        return discountUsage.getId();
    }
}
