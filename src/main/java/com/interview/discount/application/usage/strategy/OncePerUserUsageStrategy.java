package com.interview.discount.application.usage.strategy;

import com.interview.discount.domain.CountryCode;
import com.interview.discount.domain.Discount;
import com.interview.discount.domain.DiscountUsageId;
import com.interview.discount.domain.DiscountUsageRepository;
import com.interview.discount.domain.UserEmail;
import com.interview.discount.domain.exception.DiscountAlreadyUsed;
import org.springframework.stereotype.Component;

@Component
public class OncePerUserUsageStrategy extends DiscountUsageStrategy {

    public OncePerUserUsageStrategy(DiscountUsageRepository discountUsageRepository) {
        super(discountUsageRepository);
    }

    @Override
    public DiscountUsageId use(Discount discount, UserEmail userEmail, CountryCode userCountry) {
        boolean isUsed = discountUsageRepository.existsByUserEmailAndDiscountId(userEmail, discount.getId());
        if (isUsed) {
            throw new DiscountAlreadyUsed("User:" + userEmail.userEmail() + " already used discount");
        }
        return createUsage(discount, userEmail, userCountry);
    }
}
