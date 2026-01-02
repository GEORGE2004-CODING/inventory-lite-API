package com.example.API_inventory.controller;

import com.example.API_inventory.dto.*;
import com.example.API_inventory.model.enums.MovementType;
import com.example.API_inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductCreateDTO productCreateDTO){
        ProductResponseDTO product = productService.createProduct(productCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/increase-stock/{id}")
    public ResponseEntity<ProductResponseDTO> increaseStockOfProduct(@PathVariable Long id,@Valid @RequestBody ProductStockUpdateDTO productStockUpdateDTO){
        ProductResponseDTO product = productService.increaseStock(id, productStockUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/decrease-stock/{id}")
    public ResponseEntity<ProductResponseDTO> decreaseStockOfProduct(@PathVariable Long id,@Valid @RequestBody ProductStockUpdateDTO productStockUpdateDTO){
        ProductResponseDTO product = productService.decreaseStock(id, productStockUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id){
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String name,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ){
        Page<ProductResponseDTO> products = productService.getAllProducts(name, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<Page<MovementsResponseDTO>> getMovementsOfProduct(
            @PathVariable Long id,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.getMovementsOfProduct(id, pageable)
        );
    }

    @GetMapping("/{id}/movements/filters")
    public ResponseEntity<Page<MovementsResponseDTO>> getMovementsOfProduct(
            @PathVariable Long id,
            @RequestParam(required = false) MovementType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        InventoryMovementFilter filter = new InventoryMovementFilter();
        filter.setType(type);
        filter.setFrom(from);
        filter.setTo(to);

        return ResponseEntity.ok(
                productService.getMovementsOfProduct(id, filter, pageable)
        );
    }




}
