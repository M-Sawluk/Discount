package com.interview.discount.application;

import com.interview.discount.application.validation.ValidCountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DiscountCreationRequest(
    @Schema(type = "string", example = "SPRING", description = "Unique discount code")
    @NotNull(message = "code cannot be null")
    String code,
    @Min(value = 1L, message = "Max usage count cannot be lower than 1")
    @Schema(type = "number", example = "1", description = "Defines number of possible discount usages")
    int maxUsageCount,
    @NotNull
    @Schema(type = "string", example = "PL", description = "ISO 3166 two-letter code codes entitled to use discount")
    @ValidCountryCode
    String entitledCountry,
    @NotNull
    @Pattern(regexp = "ONCE_PER_USER|NO_LIMITATION", message = "Usage type is incorrect")
    @Schema(type = "string", example = "NO_LIMITATION", description = "Defines whether discount can be use once per customer or has no limitation",
            allowableValues = {"ONCE_PER_USER", "NO_LIMITATION"})
    String usageType) {
}
