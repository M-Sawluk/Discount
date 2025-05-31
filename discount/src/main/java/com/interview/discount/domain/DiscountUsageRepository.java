package com.interview.discount.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscountUsageRepository extends JpaRepository<DiscountUsage, UUID> {

    boolean existsByUserEmailAndDiscountId(UserEmail userEmail, DiscountId discountId);
}
