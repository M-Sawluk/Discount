package com.interview.discount.api;

import com.interview.discount.application.DiscountUsageCreationRequest;
import com.interview.discount.application.DiscountUsageCreationService;
import com.interview.discount.domain.DiscountUsageId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/discount-usages")
public class DiscountUsageController {

    private final DiscountUsageCreationService discountUsageCreationService;

    @PostMapping
    @Operation(
            summary = "Create usage of a discount",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Discount used successfully",
                            headers = {
                                    @Header(name = "Location", description = "URI of the newly created discount usage", schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string"))
                            }
                    )
            }
    )
    ResponseEntity<Void> createUsage(@Valid @RequestBody DiscountUsageCreationRequest usageCreateRequest, @RequestHeader(name = "X-Forwarded-For") String ip) {
        DiscountUsageId id = discountUsageCreationService.execute(usageCreateRequest, ip);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id.value())
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }
}
