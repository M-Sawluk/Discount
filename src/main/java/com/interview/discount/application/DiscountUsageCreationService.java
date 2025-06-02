package com.interview.discount.application;

import com.interview.discount.application.usage.strategy.NoLimitationUsageStrategy;
import com.interview.discount.application.usage.strategy.OncePerUserUsageStrategy;
import com.interview.discount.domain.CountryCode;
import com.interview.discount.domain.Discount;
import com.interview.discount.domain.DiscountCode;
import com.interview.discount.domain.DiscountRepository;
import com.interview.discount.domain.DiscountUsageId;
import com.interview.discount.domain.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class DiscountUsageCreationService {

    private final DiscountRepository discountRepository;
    private final IpToCountryCodeResolver ipToCountryCodeResolver;
    private final TransactionTemplate transactionTemplate;
    private final NoLimitationUsageStrategy noLimitationUsageStrategy;
    private final OncePerUserUsageStrategy oncePerUserUsageStrategy;

    public DiscountUsageId execute(DiscountUsageCreationRequest request, String customerIdAddress) {
        CountryCode customerCountryCode = ipToCountryCodeResolver.resolve(customerIdAddress);

        return transactionTemplate.execute(action -> {
            Discount discount = discountRepository.findByCodeOrThrow(new DiscountCode(request.discountCode()));
            UserEmail userEmail = new UserEmail(request.emailAddress());
            return switch (discount.getUsageType()) {
                case NO_LIMITATION -> noLimitationUsageStrategy.use(discount, userEmail, customerCountryCode);
                case ONCE_PER_USER -> oncePerUserUsageStrategy.use(discount, userEmail, customerCountryCode);
            };
        });
    }
}
