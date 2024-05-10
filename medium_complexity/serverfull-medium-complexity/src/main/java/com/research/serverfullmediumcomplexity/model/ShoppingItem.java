package com.research.serverfullmediumcomplexity.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "shopping_item")
public class ShoppingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private double price;
    private int quantity;
    private String stockDate;

    public ShoppingItem(Object o, String name, double price, int quantity, String stockDate) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.stockDate = stockDate;
    }
}
