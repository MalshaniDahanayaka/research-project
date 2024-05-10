package com.research.serverfullmediumcomplexity.controller;

import com.research.serverfullmediumcomplexity.dto.ShoppingItemRequest;
import com.research.serverfullmediumcomplexity.model.ShoppingItem;
import com.research.serverfullmediumcomplexity.service.ShoppingItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-item")
public class ShoppingItemController {

    @Autowired
    private ShoppingItemService shoppingItemService;

    @PostMapping("/save")
    public String saveShoppingItem(@RequestBody ShoppingItemRequest shoppingItem) {
        return shoppingItemService.saveShoppingItem(shoppingItem);
    }

    @GetMapping("/all")
    public List<ShoppingItem> getAllShoppingItems() {
        return shoppingItemService.getAllShoppingItems();
    }

}
