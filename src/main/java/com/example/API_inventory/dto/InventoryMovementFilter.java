package com.example.API_inventory.dto;

import com.example.API_inventory.model.enums.MovementType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryMovementFilter {

    private MovementType type;

    private LocalDate from;
    private LocalDate to;
}
