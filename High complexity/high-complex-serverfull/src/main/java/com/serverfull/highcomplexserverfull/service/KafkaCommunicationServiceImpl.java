package com.serverfull.highcomplexserverfull.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverfull.highcomplexserverfull.config.TopicsNames;
import com.serverfull.highcomplexserverfull.dto.AcceptedBookings;
import com.serverfull.highcomplexserverfull.dto.AvailableTimeSlot;
import com.serverfull.highcomplexserverfull.dto.BookedTimeSlotDetails;
import com.serverfull.highcomplexserverfull.model.AcceptedBookingTimeslot;
import com.serverfull.highcomplexserverfull.model.AvailableTimeSlots;
import com.serverfull.highcomplexserverfull.repository.AcceptedBookingRepository;
import com.serverfull.highcomplexserverfull.repository.AvailableTimeSlotsRepository;
import com.serverfull.highcomplexserverfull.repository.BookedTimeSlotRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Getter
@RequiredArgsConstructor
@Service
public class KafkaCommunicationServiceImpl implements KafkaCommunicationService {


    private final List<AvailableTimeSlot> availableTimeSlots = new ArrayList<>();

    private final List<AcceptedBookings> acceptedBookings = new ArrayList<>();

    private final KafkaTemplate<String, BookedTimeSlotDetails> kafkaTemplateForBookedTimeSlot;

    private final AcceptedBookingRepository acceptedBookingTimeSlotRepository;

    private final BookedTimeSlotRepository bookedTimeSlotRepository;

    private final AvailableTimeSlotsRepository availableTimeSlotRepository;



    @KafkaListener(topics = TopicsNames.AVAILABLE_TIME_SLOTS, groupId = "user-group")
    public void availableTimeSlots(List<AvailableTimeSlot> availableTimeSlot) {
        try {
            if (!availableTimeSlot.isEmpty()) {
                String object = availableTimeSlot.toString();
                String json = object.substring(1, object.length() - 1);
                ObjectMapper objectMapper = new ObjectMapper();
                AvailableTimeSlot resultObject = objectMapper.readValue(json, AvailableTimeSlot.class);
                if (resultObject.getStatus().equals("available")) {
                    availableTimeSlots.add(resultObject);
                    AvailableTimeSlots availableTimeSlotsObject = AvailableTimeSlots.builder()
                            .key(resultObject.getKey())
                            .status(resultObject.getStatus())
                            .userEmailAddress(resultObject.getUserEmailAddress())
                            .timeSlotAllocatedDate(resultObject.getTimeSlotAllocatedDate())
                            .timeSlotAllocatedTime(resultObject.getTimeSlotAllocatedTime())
                            .timeSlotAllocatedDuration(resultObject.getTimeSlotAllocatedDuration())
                            .timeSlotAllocatedService(resultObject.getTimeSlotAllocatedService())
                            .numberOfVehiclesMaxAllowedForService(resultObject.getNumberOfVehiclesMaxAllowedForService())
                            .availableBookingCountForService(resultObject.getAvailableBookingCountForService())
                            .timeSlotAllocatedServiceDiscount(resultObject.getTimeSlotAllocatedServiceDiscount())
                            .build();
                    System.out.println(availableTimeSlotsObject);
                    availableTimeSlotRepository.save(availableTimeSlotsObject);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @KafkaListener(topics = TopicsNames.ACCEPTED_BOOKING, groupId = "user-group")
    public void acceptedTimeSlots(List<AcceptedBookings> acceptedBookings) {
        try {
            if (!acceptedBookings.isEmpty()) {
                System.out.println(acceptedBookings);
                String object = acceptedBookings.toString();
                String json = object.substring(1, object.length() - 1);
                ObjectMapper objectMapper = new ObjectMapper();
                AcceptedBookings resultObject = objectMapper.readValue(json, AcceptedBookings.class);
                this.acceptedBookings.add(resultObject);
                bookedTimeSlotRepository.deleteByKey(resultObject.getKey());

                AcceptedBookingTimeslot acceptedBookingTimeslot = AcceptedBookingTimeslot.builder()
                        .key(resultObject.getKey())
                        .status(resultObject.getStatus())
                        .acceptedStatus(resultObject.getAcceptedStatus())
                        .userEmailAddress(resultObject.getUserEmailAddress())
                        .timeSlotAllocatedDate(resultObject.getTimeSlotAllocatedDate())
                        .timeSlotAllocatedTime(resultObject.getTimeSlotAllocatedTime())
                        .timeSlotAllocatedDuration(resultObject.getTimeSlotAllocatedDuration())
                        .timeSlotAllocatedService(resultObject.getTimeSlotAllocatedService())
                        .numberOfVehiclesMaxAllowedForService(resultObject.getNumberOfVehiclesMaxAllowedForService())
                        .availableBookingCountForService(resultObject.getAvailableBookingCountForService())
                        .timeSlotAllocatedServiceDiscount(resultObject.getTimeSlotAllocatedServiceDiscount())
                        .build();

                acceptedBookingTimeSlotRepository.save(acceptedBookingTimeslot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendBookedTimeSlot(BookedTimeSlotDetails bookedTimeSlotDetails) {
        kafkaTemplateForBookedTimeSlot.send(TopicsNames.BOOKED_TIME_SLOTS, bookedTimeSlotDetails);
    }


}
