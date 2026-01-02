package com.example.API_inventory.service;

import com.example.API_inventory.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO);
    ProductResponseDTO increaseStock(Long id, ProductStockUpdateDTO productStockUpdateDTO);
    ProductResponseDTO decreaseStock(Long id, ProductStockUpdateDTO productStockUpdateDTO);
    ProductResponseDTO getProductById(Long id);
    Page<ProductResponseDTO> getAllProducts(String name, Pageable pageable);
    Page<MovementsResponseDTO> getMovementsOfProduct(Long id, Pageable pageable);
    Page<MovementsResponseDTO> getMovementsOfProduct(Long productId, InventoryMovementFilter filter, Pageable pageable);
}
