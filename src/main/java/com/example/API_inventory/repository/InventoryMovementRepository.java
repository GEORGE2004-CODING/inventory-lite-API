package com.example.API_inventory.repository;

import com.example.API_inventory.model.InventoryMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMovementRepository extends
        JpaRepository<InventoryMovement, Long>,
        JpaSpecificationExecutor<InventoryMovement> {
    Page<InventoryMovement> findByProductId(Long productId, Pageable pageable);

}
