package com.serverfull.highcomplexserverfull.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookedTimeSlotDetails {

    private String key;

    private String status;

    private String userEmailAddress;

    private String acceptedStatus;

    private String timeSlotAllocatedDate;

    private String timeSlotAllocatedTime;

    private String timeSlotAllocatedDuration;

    private String timeSlotAllocatedService;

    private int numberOfVehiclesMaxAllowedForService;

    private int availableBookingCountForService;

    private Double timeSlotAllocatedServiceDiscount;


}