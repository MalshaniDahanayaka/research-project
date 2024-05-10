package com.research.serverfullmediumcomplexity.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ShoppingItemRequest {

    private String name;
    private double price;
    private int quantity;
    private String stockDate;
}
