package com.example.API_inventory.mapper;

import com.example.API_inventory.dto.ProductCreateDTO;
import com.example.API_inventory.dto.ProductResponseDTO;
import com.example.API_inventory.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponseDTO(Product product){
        return ProductResponseDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public Product toEntity(ProductCreateDTO productCreateDTO){
        return Product.builder()
                .name(productCreateDTO.getName())
                .description(productCreateDTO.getDescription())
                .stock(productCreateDTO.getStock())
                .price(productCreateDTO.getPrice())
                .build();
    }
}
