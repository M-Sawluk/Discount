package com.interview.discount.domain;

import com.interview.discount.domain.exception.DiscountNotFoundException;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Discount> findByCode(DiscountCode discountCode);

    boolean existsByCode(DiscountCode discountCode);

    default Discount findByCodeOrThrow(DiscountCode discountCode) {
        return findByCode(discountCode)
                .orElseThrow(() -> new DiscountNotFoundException("Discount with code: " + discountCode.code() + " cannot be found"));
    }
}
