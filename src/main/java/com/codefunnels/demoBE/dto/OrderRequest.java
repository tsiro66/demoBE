package com.codefunnels.demoBE.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    // --- Product Details (for the single product) ---
    // Since it's a single-product store, the product ID is implicitly 1,
    // but including it makes the DTO reusable if you ever expand.
    @NotNull(message = "Product ID is required")
    private Long productId = 1L;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    // For a simple checkout flow, we collect customer data without assuming login.
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Street address is required")
    private String streetAddress;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Zip code is required")
    private String zipCode;

    // In a real application, this would be a secure token from a payment processor (e.g., Stripe)
    @NotBlank(message = "Payment token is required")
    private String paymentToken;
}