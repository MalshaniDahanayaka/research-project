package salesmanagement.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SaleDetailsDto {

    private String productName;
    private double unitPrice;
    private int quantity;
    private Date salesDate;

}
