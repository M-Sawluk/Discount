package com.interview.discount.domain;

import com.interview.discount.domain.exception.DiscountUsageLimitReachedException;
import com.interview.discount.domain.exception.DiscountNotEntitledInUserCountryException;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Entity
@DynamicUpdate
@Table(name = "discount", uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@NoArgsConstructor
public class Discount {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private DiscountId id;
    @NotNull
    @Embedded
    private DiscountCode code;
    @NotNull
    private Instant creationDate;
    @Min(value = 1L, message = "Starting usage count must be positive number")
    private int maxUsageCount;
    @Min(value = 0L, message = "Remaining usage count cannot be lower than 0")
    private int currentUsageCount;
    @NotNull
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "entitled_country"))
    private CountryCode entitledCountry;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscountUsageType usageType;
    @Version
    private Long version;

    public Discount(DiscountCode code, int maxUsageCount, CountryCode entitledCountry, DiscountUsageType usageType, Clock clock) {
        this.id = new DiscountId(UUID.randomUUID());
        this.code = code;
        this.creationDate = clock.instant();
        this.maxUsageCount = maxUsageCount;
        this.currentUsageCount = 0;
        this.entitledCountry = entitledCountry;
        this.usageType = usageType;
    }

    public void useDiscount(CountryCode userOriginCountry) {
        if (!entitledCountry.value().equals(userOriginCountry.value())) {
            throw new DiscountNotEntitledInUserCountryException(userOriginCountry);
        }
        if (currentUsageCount == maxUsageCount) {
            throw new DiscountUsageLimitReachedException();
        }
        this.currentUsageCount = currentUsageCount + 1;
    }

    public DiscountId getId() {
        return id;
    }

    public DiscountUsageType getUsageType() {
        return usageType;
    }
}
