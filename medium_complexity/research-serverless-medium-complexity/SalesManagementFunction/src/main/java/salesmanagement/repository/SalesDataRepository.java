package salesmanagement.repository;


import salesmanagement.db.DatabaseConnection;
import salesmanagement.dto.SaleDetailsDto;
import salesmanagement.model.SalesDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesDataRepository {

    private static SalesDataRepository instance = null;
    private DatabaseConnection connection = DatabaseConnection.getInstance();

    public static SalesDataRepository getInstance() {
        if (instance == null) {
            instance = new SalesDataRepository();
        }
        return instance;
    }

    public void saveSaleDetail(SaleDetailsDto salesDetail) throws SQLException {

        Connection con = null;
        SalesDetail newSaleDetail = new SalesDetail(null, salesDetail.getProductName(), salesDetail.getUnitPrice(), salesDetail.getQuantity(), salesDetail.getSalesDate());
        String query = "INSERT INTO sales_detail (product_name, unit_price, quantity, sales_date) VALUES (?, ?, ?, ?)";
        try {
            con = connection.getConnection();
            if(con != null) {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, newSaleDetail.getProductName());
                preparedStatement.setDouble(2, newSaleDetail.getUnitPrice());
                preparedStatement.setInt(3, newSaleDetail.getQuantity());
                preparedStatement.setDate(4, new java.sql.Date(newSaleDetail.getSalesDate().getTime()));
                preparedStatement.executeUpdate();
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public List<SalesDetail> getAllSalesDetails() throws SQLException {
        List<SalesDetail> response = new ArrayList<>();
        String query = "SELECT * FROM sales_detail";
        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String productName = resultSet.getString("product_name");
                double unitPrice = resultSet.getDouble("unit_price");
                int quantity = resultSet.getInt("quantity");
                Date salesDate = resultSet.getDate("sales_date");
                SalesDetail salesDetail = new SalesDetail(id, productName, unitPrice, quantity, salesDate);
                response.add(salesDetail);
            }
        }
        return response;
    }


}
