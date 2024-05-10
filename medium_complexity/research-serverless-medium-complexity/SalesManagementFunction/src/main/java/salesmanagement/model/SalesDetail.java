package salesmanagement.model;

import lombok.Data;

import java.util.Date;

@Data
public class SalesDetail {

    private int id;
    private String productName;
    private double unitPrice;
    private int quantity;
    private Date salesDate;

    public SalesDetail(Object o, String productName, double unitPrice, int quantity, Date salesDate) {
        this.id = id;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.salesDate = salesDate;
    }
}
