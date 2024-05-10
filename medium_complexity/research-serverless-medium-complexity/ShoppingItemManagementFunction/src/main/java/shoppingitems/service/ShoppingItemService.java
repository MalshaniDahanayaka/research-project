package shoppingitems.service;

import shoppingitems.model.ShoppingItem;
import shoppingitems.repository.ShoppingItemRepository;

import java.sql.SQLException;
import java.util.List;

public class ShoppingItemService{

    private static ShoppingItemService instance;
    private final ShoppingItemRepository shoppingItemRepository = ShoppingItemRepository.getInstance();


    public static ShoppingItemService getInstance() {
        if (instance == null) {
            instance = new ShoppingItemService();
        }
        return instance;
    }

    public String saveShoppingItem(ShoppingItem shoppingItem) throws SQLException {
         shoppingItemRepository.saveShoppingItem(shoppingItem);
         return "ShoppingItem saved successfully";
    }

    public List<ShoppingItem> getAllShoppingItems() throws SQLException {
        return shoppingItemRepository.getAllShoppingItems();
    }

}
