package shoppingitems.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ShoppingItem {

    private int id;
    private String name;
    private double price;
    private int quantity;
    private String stockDate;

    public ShoppingItem(int primaryKey, String name, double price, int quantity, String stockDate) {
        this.id = primaryKey;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.stockDate = stockDate;
    }
}
