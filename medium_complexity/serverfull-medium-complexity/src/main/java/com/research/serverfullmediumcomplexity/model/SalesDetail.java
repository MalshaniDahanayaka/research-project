package com.research.serverfullmediumcomplexity.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "sales_detail")
public class SalesDetail {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String productName;
    private double unitPrice;
    private int quantity;
    private Date salesDate;

    public SalesDetail(Long id, String productName, double unitPrice, int quantity, Date salesDate) {
        this.id = id;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.salesDate = salesDate;
    }
}