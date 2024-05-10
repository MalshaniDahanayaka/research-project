package com.realTimeData.provider.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.realTimeData.provider.config.Constants;
import com.realTimeData.provider.dto.AcceptedBookings;
import com.realTimeData.provider.dto.AvailableTimeSlot;
import com.realTimeData.provider.dto.BookedTimeSlotDetails;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirlineService {

    private final KafkaTemplate<String, AvailableTimeSlot> kafkaTemplate;

    private final KafkaTemplate<String, AcceptedBookings> kafkaTemplateForBookingAcceptance;

    public final List<BookedTimeSlotDetails> bookedTimeSlotDetailsList = new ArrayList<>();


    public AirlineService(KafkaTemplate<String, AvailableTimeSlot> kafkaTemplate, KafkaTemplate<String, AcceptedBookings> kafkaTemplateForBookingAcceptance) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateForBookingAcceptance = kafkaTemplateForBookingAcceptance;
    }

    public boolean addServiceBookingTimeslot(AvailableTimeSlot availableTimeSlot) {
        kafkaTemplate.send(Constants.AVAILABLE_TIME_SLOTS, availableTimeSlot);
        return true;
    }

    public boolean deleteBookingTimeslot(AvailableTimeSlot availableTimeSlot, String key) {
        kafkaTemplate.send(Constants.AVAILABLE_TIME_SLOTS, key ,availableTimeSlot);
        return true;
    }


    public List<BookedTimeSlotDetails> bookedTimeSlotDetailsList() {
        return bookedTimeSlotDetailsList;
    }

    public boolean acceptTimeSlot(AcceptedBookings acceptedBookings) {
        kafkaTemplateForBookingAcceptance.send(Constants.ACCEPTED_BOOKING, acceptedBookings);
        return true;
    }

    @KafkaListener(topics = "booked-time-slots", groupId = "user-group")
    public void bookedTimeSlots(List<BookedTimeSlotDetails> bookedTimeSlotDetails){
        try {
            if (!bookedTimeSlotDetails.isEmpty()) {
                System.out.println(bookedTimeSlotDetails.toString());
                String object = bookedTimeSlotDetails.toString();
                String json = object.substring(1, object.length() - 1);
                ObjectMapper objectMapper = new ObjectMapper();
                BookedTimeSlotDetails resultObject = objectMapper.readValue(json, BookedTimeSlotDetails.class);
                bookedTimeSlotDetailsList.add(resultObject);

                //delete the available timeslot
                AvailableTimeSlot availableTimeSlot = AvailableTimeSlot.builder()
                        .key(resultObject.getKey())
                        .status("deleted")
                        .build();
                // deleteBookingTimeslot(availableTimeSlot, resultObject.getKey());
                System.out.println(resultObject.getKey());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}