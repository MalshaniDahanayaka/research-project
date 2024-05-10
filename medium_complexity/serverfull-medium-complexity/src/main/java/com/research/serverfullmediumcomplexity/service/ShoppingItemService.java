package com.research.serverfullmediumcomplexity.service;

import com.research.serverfullmediumcomplexity.dto.ShoppingItemRequest;
import com.research.serverfullmediumcomplexity.model.ShoppingItem;
import com.research.serverfullmediumcomplexity.repository.ShoppingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingItemService {

    @Autowired
    private ShoppingItemRepository shoppingItemRepository;

    public String saveShoppingItem(ShoppingItemRequest shoppingItem) {
        ShoppingItem shoppingItemObj = new ShoppingItem(
                null,
                shoppingItem.getName(),
                shoppingItem.getPrice(),
                shoppingItem.getQuantity(),
                shoppingItem.getStockDate()
        );

        shoppingItemRepository.saveAndFlush(shoppingItemObj);
        return "ShoppingItem saved successfully";
    }

    public List<ShoppingItem> getAllShoppingItems() {
        return shoppingItemRepository.findAll();
    }
}
