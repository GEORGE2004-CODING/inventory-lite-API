package com.example.API_inventory.model;


import com.example.API_inventory.model.enums.MovementType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inventory_movements")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigInteger quantity;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
}
