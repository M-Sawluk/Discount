package com.interview.discount.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
public class DiscountUsage {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private DiscountUsageId id;
    @AttributeOverride(name = "value", column = @Column(name = "discount_id"))
    private DiscountId discountId;
    @Embedded
    private UserEmail userEmail;

    public DiscountUsage(Discount discount, UserEmail userEmail) {
        this.id = new DiscountUsageId(UUID.randomUUID());
        this.discountId = discount.getId();
        this.userEmail = userEmail;
    }

    public DiscountUsageId getId() {
        return id;
    }
}
