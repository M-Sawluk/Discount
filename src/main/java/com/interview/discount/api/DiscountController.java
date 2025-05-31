package com.interview.discount.api;

import com.interview.discount.application.DiscountCreationRequest;
import com.interview.discount.application.DiscountCreationService;
import com.interview.discount.domain.DiscountId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/discounts")
public class DiscountController {

    public final DiscountCreationService discountCreationService;

    @PostMapping
    @Operation(
            summary = "Create a new discount",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Discount created successfully",
                            headers = {
                                    @Header(name = "Location", description = "URI of the newly created discount", schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string"))
                            },
                            content = @Content
                    )
            }
    )
    ResponseEntity<Void> createDiscount(@Valid @RequestBody DiscountCreationRequest discountCreationRequest) {
        DiscountId id = discountCreationService.execute(discountCreationRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id.value())
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }
}
