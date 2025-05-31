package com.interview.discount.fixtures;

import com.interview.discount.application.DiscountUsageCreationRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class DiscountUsageTestData {

    public static String DISCOUNT_CODE = "Spring";
    public static String EMAIL = "abc@gmail.com";
    public static String IP_DE = "77.1.2.3";
    public static String IP_PL = "5.201.0.0";

    public static HttpEntity<DiscountUsageCreationRequest> createDiscountUsageRequestHttpEntity(String discountCode, String ip) {
        DiscountUsageCreationRequest request = new DiscountUsageCreationRequest(discountCode, EMAIL);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Forwarded-For", ip);
        return new HttpEntity<>(request, headers);
    }

}
