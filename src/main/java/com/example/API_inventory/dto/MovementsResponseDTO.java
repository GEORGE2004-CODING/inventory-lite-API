package com.example.API_inventory.dto;

import com.example.API_inventory.model.enums.MovementType;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovementsResponseDTO {

    private BigInteger quantity;

    private MovementType movementType;

    private LocalDateTime createdAt;

}
