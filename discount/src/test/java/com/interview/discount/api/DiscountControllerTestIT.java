package com.interview.discount.api;

import com.interview.discount.application.DiscountCreationRequest;
import com.interview.discount.fixtures.DiscountTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static com.interview.discount.fixtures.DiscountTestData.DISCOUNT_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiscountControllerTestIT {
    private static final String HOST = "http://localhost:";
    private static final String DISCOUNTS_URL = "/discount/v1.0/discounts";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateDiscount() {
        //when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_URL, DISCOUNT_CREATE_REQUEST, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    void shouldThrow400WhenDuplicateDiscountCode() {
        // given
        String SUMMER_UPPERCASE = "SUMMER";
        String SUMMER = "Summer";
        restTemplate.postForEntity(HOST + port + DISCOUNTS_URL, DiscountTestData.createDiscountRequest(SUMMER_UPPERCASE), String.class);

        //when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_URL, DiscountTestData.createDiscountRequest(SUMMER), String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Discount with code: Summer already exists");
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",1,DE,NO_LIMITATION, code cannot be null",
            "A,0,DE,NO_LIMITATION, Max usage count cannot be lower than 1",
            "B,1,DE,LIMITATION, Usage type is incorrect",
            "B,1,DEA,NO_LIMITATION, Invalid country code",
    })
    void shouldReturnValidationErrorsWithStatus400(String code, int maxUsage, String countryCode, String usageType, String errorMessage) {
        //given
        DiscountCreationRequest request = new DiscountCreationRequest(code, maxUsage, countryCode, usageType);

        //when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + DISCOUNTS_URL, request, String.class);

        //then
        assertThat(response.getBody()).isEqualTo(errorMessage);
    }
}
