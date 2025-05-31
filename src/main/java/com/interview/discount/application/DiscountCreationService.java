package com.interview.discount.application;

import com.interview.discount.application.factory.DiscountFactory;
import com.interview.discount.domain.Discount;
import com.interview.discount.domain.DiscountCode;
import com.interview.discount.domain.DiscountId;
import com.interview.discount.domain.DiscountRepository;
import com.interview.discount.domain.exception.DiscountAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiscountCreationService {

    private final DiscountRepository discountRepository;
    private final DiscountFactory discountFactory;

    @Transactional
    public DiscountId execute(DiscountCreationRequest request) {
        if (discountRepository.existsByCode(new DiscountCode(request.code()))) {
            throw new DiscountAlreadyExistsException(request.code());
        }
        Discount discount = discountFactory.create(request);
        discountRepository.save(discount);

        return discount.getId();
    }

}
