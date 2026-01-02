package com.example.API_inventory.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {

    @NotBlank(message = "Name of product is mandatory!!!")
    @Size(min = 2, max = 100)
    private String name;

    @Size(min = 2, max = 600)
    private String description;

    @NotNull(message = "Quantity or stock is mandatory!!!")
    @Positive(message = "Quantity or stock is greather than 0")
    private BigInteger stock;

    @NotNull(message = "Price is mandatory!!!")
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "100000.00")
    @Digits(integer = 10, fraction = 2, message = "Invalid format price!!!")
    private BigDecimal price;

}
