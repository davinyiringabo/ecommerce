package com.ecommerce.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductDto {
    @NotBlank(message = "Product name is required!")
    private String productName;

    @NotBlank(message = "Product description is required!")
    private String description;

    @Positive(message = "Price is positive")
    private Double price;

    @NotBlank(message = "Product picture must be uploaded")
    private String imageUrl;

    @Positive(message = "Product quantity required")
    private int quantity;

}
