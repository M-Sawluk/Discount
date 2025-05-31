package com.interview.discount.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DiscountUsageCreationRequest(@NotNull(message = "Discount code cannot be null")
                                           String discountCode,
                                           @NotNull(message = "Email address cannot be null")
                                           @Email
                                           String emailAddress) {
}
