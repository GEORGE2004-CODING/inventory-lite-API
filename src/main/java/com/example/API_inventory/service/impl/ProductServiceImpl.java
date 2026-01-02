package com.example.API_inventory.service.impl;

import com.example.API_inventory.dto.*;
import com.example.API_inventory.exception.InvalidPriceProductException;
import com.example.API_inventory.exception.InvalidStockProductException;
import com.example.API_inventory.exception.ProductNotFoundException;
import com.example.API_inventory.mapper.MovementMapper;
import com.example.API_inventory.mapper.ProductMapper;
import com.example.API_inventory.model.InventoryMovement;
import com.example.API_inventory.model.Product;
import com.example.API_inventory.model.enums.MovementType;
import com.example.API_inventory.repository.InventoryMovementRepository;
import com.example.API_inventory.repository.ProductRepository;
import com.example.API_inventory.service.ProductService;
import com.example.API_inventory.specification.InventoryMovementSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final MovementMapper movementMapper;
    private final InventoryMovementRepository movementRepository;

    @Transactional
    @Override
    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO) {

        if (productCreateDTO.getPrice().compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidPriceProductException("The price must be greater than 0");
        }

        if (productCreateDTO.getStock().compareTo(BigInteger.ZERO) < 0){
            throw new InvalidStockProductException("The stock must be greater than 0");
        }

        Product product = productMapper.toEntity(productCreateDTO);
        productRepository.save(product);

        InventoryMovement inventoryMovement = InventoryMovement.builder()
                .quantity(productCreateDTO.getStock())
                .movementType(MovementType.INCREASE)
                .product(product)
                .build();

        movementRepository.save(inventoryMovement);

        return productMapper.toResponseDTO(product);
    }

    @Transactional
    @Override
    public ProductResponseDTO increaseStock(Long id, ProductStockUpdateDTO productStockUpdate) {

        Product product = findProduct(id);

        if (productStockUpdate.getQuantity().compareTo(BigInteger.ZERO) < 0){
            throw new InvalidStockProductException("The amount of stock to add cannot be negative");
        }

        product.setStock(product.getStock().add(productStockUpdate.getQuantity()));

        InventoryMovement inventoryMovement = InventoryMovement
                .builder()
                .movementType(MovementType.INCREASE)
                .quantity(productStockUpdate.getQuantity())
                .product(product)
                .build();

        product.getInventoryMovement().add(inventoryMovement);


        productRepository.save(product);
        movementRepository.save(inventoryMovement);

        return productMapper.toResponseDTO(product);
    }

    @Transactional
    @Override
    public ProductResponseDTO decreaseStock(Long id, ProductStockUpdateDTO productStockUpdateDTO) {

        Product product = findProduct(id);

        if (product.getStock().compareTo(productStockUpdateDTO.getQuantity()) < 0){
            throw new InvalidStockProductException("The amount of stock to be eliminated exceeds the existing stock");
        }

        product.setStock(product.getStock().subtract(productStockUpdateDTO.getQuantity()));

        InventoryMovement inventoryMovement = InventoryMovement
                .builder()
                .movementType(MovementType.DECREASE)
                .quantity(productStockUpdateDTO.getQuantity())
                .product(product)
                .build();

        product.getInventoryMovement().add(inventoryMovement);


        productRepository.save(product);
        movementRepository.save(inventoryMovement);

        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = findProduct(id);

        return productMapper.toResponseDTO(product);
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(String name, Pageable pageable) {
        if (name == null || name.trim().isEmpty()) {
            Page<Product> products = productRepository.findAll(pageable);
            return products.map(productMapper::toResponseDTO);
        }

        Page<Product> products = productRepository.findByNameContaining(name, pageable);

        return products.map(productMapper::toResponseDTO);
    }

    @Override
    public Page<MovementsResponseDTO> getMovementsOfProduct(Long id, Pageable pageable) {
        findProduct(id);

        Page<InventoryMovement> movements =
                movementRepository.findByProductId(id, pageable);

        return movements.map(movementMapper::toResponseDTO);
    }

    @Override
    public Page<MovementsResponseDTO> getMovementsOfProduct(
            Long productId,
            InventoryMovementFilter filter,
            Pageable pageable
    ) {
        findProduct(productId); // valida existencia

        Specification<InventoryMovement> spec =
                InventoryMovementSpecification.withFilters(productId, filter);

        return movementRepository
                .findAll(spec, pageable)
                .map(movementMapper::toResponseDTO);
    }


    /*====================================================*/

    private Product findProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID: " + id + "not found"));
    }
}
