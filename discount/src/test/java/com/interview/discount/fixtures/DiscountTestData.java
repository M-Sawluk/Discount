package com.interview.discount.fixtures;


import com.interview.discount.application.DiscountCreationRequest;

public class DiscountTestData {

    public static String DISCOUNT_CODE = "Spring";
    public static int MAX_USAGE_COUNT = 1;
    public static String ENTITLED_COUNTRY = "DE";
    public static String USAGE_TYPE_NO_LIMITATION = "NO_LIMITATION";
    public static String USAGE_TYPE_ONCE_PER_USER = "ONCE_PER_USER";

    public static DiscountCreationRequest DISCOUNT_CREATE_REQUEST = new DiscountCreationRequest(DISCOUNT_CODE, MAX_USAGE_COUNT, ENTITLED_COUNTRY, USAGE_TYPE_NO_LIMITATION);

    public static DiscountCreationRequest createDiscountRequest(String discountCode) {
        return new DiscountCreationRequest(discountCode, MAX_USAGE_COUNT, ENTITLED_COUNTRY, USAGE_TYPE_NO_LIMITATION);
    }

    public static DiscountCreationRequest createDiscountRequest(String discountCode, String usageType) {
        return new DiscountCreationRequest(discountCode, MAX_USAGE_COUNT, ENTITLED_COUNTRY, usageType);
    }

}
