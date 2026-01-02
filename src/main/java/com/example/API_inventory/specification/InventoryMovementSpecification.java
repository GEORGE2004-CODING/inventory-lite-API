package com.example.API_inventory.specification;

import com.example.API_inventory.dto.InventoryMovementFilter;
import com.example.API_inventory.model.InventoryMovement;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InventoryMovementSpecification {

    public static Specification<InventoryMovement> withFilters(
            Long productId,
            InventoryMovementFilter filter
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // producto obligatorio
            predicates.add(
                    cb.equal(root.get("product").get("id"), productId)
            );

            // tipo de movimiento
            if (filter.getType() != null) {
                predicates.add(
                        cb.equal(root.get("type"), filter.getType())
                );
            }

            // fecha desde
            if (filter.getFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("createdAt"),
                                filter.getFrom().atStartOfDay()
                        )
                );
            }

            // fecha hasta
            if (filter.getTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("createdAt"),
                                filter.getTo().atTime(23, 59, 59)
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
