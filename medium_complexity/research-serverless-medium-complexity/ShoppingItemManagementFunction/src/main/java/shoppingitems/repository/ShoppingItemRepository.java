package shoppingitems.repository;

import shoppingitems.db.DatabaseConnection;
import shoppingitems.model.ShoppingItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingItemRepository {

    private static ShoppingItemRepository instance = null;
    private DatabaseConnection connection = DatabaseConnection.getInstance();

    public static ShoppingItemRepository getInstance() {
        if (instance == null) {
            instance = new ShoppingItemRepository();
        }
        return instance;
    }

    private static int primaryKey = 0;

    public void saveShoppingItem(ShoppingItem shoppingItem) throws SQLException {

        Connection con = null;
        primaryKey++;
        ShoppingItem newShoppingItem = new ShoppingItem(primaryKey, shoppingItem.getName(), shoppingItem.getPrice(), shoppingItem.getQuantity(), shoppingItem.getStockDate());
        String query = "INSERT INTO shopping_item (id, name, price, quantity, stock_date) VALUES (?, ?, ?, ?, ?)";
        try {
            con = connection.getConnection();
            if(con != null) {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, newShoppingItem.getId());
                preparedStatement.setString(2, newShoppingItem.getName());
                preparedStatement.setDouble(3, newShoppingItem.getPrice());
                preparedStatement.setInt(4, newShoppingItem.getQuantity());
                preparedStatement.setString(5, newShoppingItem.getStockDate());
                preparedStatement.executeUpdate();
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public List<ShoppingItem> getAllShoppingItems() throws SQLException {
        List<ShoppingItem> response = new ArrayList<>();
        String query = "SELECT * FROM shopping_item";
        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String stockDate = resultSet.getString("stock_date");

                ShoppingItem shoppingItem = new ShoppingItem(id, title, price, quantity, stockDate);
                response.add(shoppingItem);
            }
        }
        return response;
    }


}
