package com.serverfull.highcomplexserverfull.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AcceptedBookingTimeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String key;

    private String status;

    private String acceptedStatus;

    private String userEmailAddress;

    private String timeSlotAllocatedDate;

    private String timeSlotAllocatedTime;

    private String timeSlotAllocatedDuration;

    private String timeSlotAllocatedService;

    private int numberOfVehiclesMaxAllowedForService;

    private int availableBookingCountForService;

    private Double timeSlotAllocatedServiceDiscount;

}