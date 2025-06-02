package com.interview.discount.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static com.interview.discount.fixtures.DiscountTestData.DISCOUNT_CODE;
import static com.interview.discount.fixtures.DiscountTestData.USAGE_TYPE_NO_LIMITATION;
import static com.interview.discount.fixtures.DiscountTestData.USAGE_TYPE_ONCE_PER_USER;
import static com.interview.discount.fixtures.DiscountTestData.createDiscountRequest;
import static com.interview.discount.fixtures.DiscountUsageTestData.IP_DE;
import static com.interview.discount.fixtures.DiscountUsageTestData.IP_PL;
import static com.interview.discount.fixtures.DiscountUsageTestData.createDiscountUsageRequestHttpEntity;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class DiscountControllerUsageTestIT {
    private static final String HOST = "http://localhost:";
    private static final String DISCOUNTS_USAGES_URL = "/discount/v1.0/discount-usages";
    private static final String DISCOUNTS_URL = "/discount/v1.0/discounts";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldUseDiscount() {
        // given
        String discountCode = "WINTER";
        var request = createDiscountUsageRequestHttpEntity(DISCOUNT_CODE, IP_DE);
        createDiscount(discountCode, USAGE_TYPE_NO_LIMITATION);
        // when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    void shouldThrow404WhenDiscountNotFound() {
        // given
        var request = createDiscountUsageRequestHttpEntity("AUTUMN", IP_DE);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldThrow400WhenDiscountNotEntitledForCountry() {
        // given
        createDiscount(DISCOUNT_CODE, USAGE_TYPE_NO_LIMITATION);
        var request = createDiscountUsageRequestHttpEntity(DISCOUNT_CODE, IP_PL);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Discount not available for: PL");
    }

    @Test
    void shouldThrow400WhenDiscountExhaused() {
        // given
        String discountCode = "BEER";
        createDiscount(discountCode, USAGE_TYPE_NO_LIMITATION);
        var request = createDiscountUsageRequestHttpEntity(discountCode, IP_DE);
        restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Discount limit reached");
    }

    @Test
    void shouldThrow400WhenDiscountUseMultipleTimeBySameUser() {
        // given
        String discountCode = "CAR";
        createDiscount(discountCode, USAGE_TYPE_ONCE_PER_USER);
        var request = createDiscountUsageRequestHttpEntity(discountCode, IP_DE);
        restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("User:abc@gmail.com already used discount");
    }

    @Test
    void shouldThrow400WhenWhenUnknownIpAddress() {
        // given
        String discountCode = "CAR";
        var request = createDiscountUsageRequestHttpEntity(discountCode, "2213.321");
        restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_USAGES_URL, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Unable to resolve IP address");
    }

    private void createDiscount(String discountCode, String usageType) {
        restTemplate.postForEntity(HOST + port + DISCOUNTS_URL, createDiscountRequest(discountCode, usageType), String.class);
    }
}
