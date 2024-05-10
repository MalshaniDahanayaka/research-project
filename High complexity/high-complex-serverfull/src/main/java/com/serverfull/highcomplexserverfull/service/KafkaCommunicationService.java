package com.serverfull.highcomplexserverfull.service;


import com.serverfull.highcomplexserverfull.dto.AcceptedBookings;
import com.serverfull.highcomplexserverfull.dto.AvailableTimeSlot;
import com.serverfull.highcomplexserverfull.dto.BookedTimeSlotDetails;

import java.util.List;

public interface KafkaCommunicationService {
    void sendBookedTimeSlot(BookedTimeSlotDetails bookedTimeSlotDetails);

    List<AvailableTimeSlot> getAvailableTimeSlots();

    List<AcceptedBookings> getAcceptedBookings();
}