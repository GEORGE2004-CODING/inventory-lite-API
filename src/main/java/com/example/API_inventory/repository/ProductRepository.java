package com.example.API_inventory.repository;

import com.example.API_inventory.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(String nombre, Pageable pageable);
    Page<Product> findAll(Pageable pageable);

}
