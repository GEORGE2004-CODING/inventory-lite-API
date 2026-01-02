package com.example.API_inventory.mapper;

import com.example.API_inventory.dto.MovementsResponseDTO;
import com.example.API_inventory.model.InventoryMovement;
import org.springframework.stereotype.Component;

@Component
public class MovementMapper {

    public MovementsResponseDTO toResponseDTO(InventoryMovement inventoryMovement){
        return MovementsResponseDTO.builder()
                .movementType(inventoryMovement.getMovementType())
                .createdAt(inventoryMovement.getCreatedAt())
                .quantity(inventoryMovement.getQuantity())
                .build();
    }
}
