package com.interview.discount.application.strategy;

import com.interview.discount.domain.CountryCode;
import com.interview.discount.domain.Discount;
import com.interview.discount.domain.DiscountUsageId;
import com.interview.discount.domain.DiscountUsageRepository;
import com.interview.discount.domain.UserEmail;
import org.springframework.stereotype.Component;

@Component
public class NoLimitationUsageStrategy extends DiscountUsageStrategy {

    public NoLimitationUsageStrategy(DiscountUsageRepository discountUsageRepository) {
        super(discountUsageRepository);
    }

    @Override
    public DiscountUsageId use(Discount discount, UserEmail userEmail, CountryCode userCountry) {
        return createUsage(discount, userEmail, userCountry);
    }
}
