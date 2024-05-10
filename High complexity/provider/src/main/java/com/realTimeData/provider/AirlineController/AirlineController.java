package com.realTimeData.provider.AirlineController;

import com.github.javafaker.Faker;
import com.realTimeData.provider.dto.AcceptedBookings;
import com.realTimeData.provider.dto.AvailableTimeSlot;
import com.realTimeData.provider.dto.BookedTimeSlotDetails;
import com.realTimeData.provider.service.AirlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/garage")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }


    @GetMapping("/booked-time-slots")
    public List<BookedTimeSlotDetails> bookedTimeSlots() {
        return airlineService.bookedTimeSlotDetailsList();
    }

    @PostMapping("/accept-time-slot")
    public ResponseEntity<Map<String, String>> acceptTimeSlot() throws InterruptedException {

        int range = 7;
        List<AcceptedBookings> acceptedBookings = new ArrayList<>(Arrays.asList(
                AcceptedBookings.builder()
                        .key(UUID.randomUUID().toString())
                        .status("available")
                        .acceptedStatus("accepted")
                        .userEmailAddress("")
                        .timeSlotAllocatedDate("2022-01-01")
                        .timeSlotAllocatedTime("09:00")
                        .timeSlotAllocatedDuration("2 hours")
                        .timeSlotAllocatedService("Oil Change")
                        .numberOfVehiclesMaxAllowedForService(5)
                        .availableBookingCountForService(5)
                        .timeSlotAllocatedServiceDiscount(0.0)
                        .build(),
                AcceptedBookings.builder()
                        .key(UUID.randomUUID().toString())
                        .status("available")
                        .acceptedStatus("accepted")
                        .userEmailAddress("")
                        .timeSlotAllocatedDate("2022-01-05")
                        .timeSlotAllocatedTime("14:00")
                        .timeSlotAllocatedDuration("3 hours")
                        .timeSlotAllocatedService("Tire Rotation")
                        .numberOfVehiclesMaxAllowedForService(8)
                        .availableBookingCountForService(8)
                        .timeSlotAllocatedServiceDiscount(0.0)
                        .build(),
                AcceptedBookings.builder()
                        .key(UUID.randomUUID().toString())
                        .status("available")
                        .acceptedStatus("accepted")
                        .userEmailAddress("")
                        .timeSlotAllocatedDate("2022-01-10")
                        .timeSlotAllocatedTime("10:30")
                        .timeSlotAllocatedDuration("1 hour")
                        .timeSlotAllocatedService("Car Wash")
                        .numberOfVehiclesMaxAllowedForService(3)
                        .availableBookingCountForService(3)
                        .timeSlotAllocatedServiceDiscount(0.0)
                        .build(),
                AcceptedBookings.builder()
                        .key(UUID.randomUUID().toString())
                        .status("available")
                        .acceptedStatus("accepted")
                        .userEmailAddress("")
                        .timeSlotAllocatedDate("2022-01-15")
                        .timeSlotAllocatedTime("11:00")
                        .timeSlotAllocatedDuration("2 hours")
                        .timeSlotAllocatedService("Brake Inspection")
                        .numberOfVehiclesMaxAllowedForService(6)
                        .availableBookingCountForService(6)
                        .timeSlotAllocatedServiceDiscount(0.0)
                        .build(),
                AcceptedBookings.builder()
                        .key(UUID.randomUUID().toString())
                        .status("available")
                        .acceptedStatus("accepted")
                        .userEmailAddress("")
                        .timeSlotAllocatedDate("2022-01-20")
                        .timeSlotAllocatedTime("13:30")
                        .timeSlotAllocatedDuration("1.5 hours")
                        .timeSlotAllocatedService("Wheel Alignment")
                        .numberOfVehiclesMaxAllowedForService(4)
                        .availableBookingCountForService(4)
                        .timeSlotAllocatedServiceDiscount(0.0)
                        .build(),
                AcceptedBookings.builder()
                        .key(UUID.randomUUID().toString())
                        .status("available")
                        .acceptedStatus("accepted")
                        .userEmailAddress("")
                        .timeSlotAllocatedDate("2022-01-25")
                        .timeSlotAllocatedTime("16:00")
                        .timeSlotAllocatedDuration("2.5 hours")
                        .timeSlotAllocatedService("Battery Replacement")
                        .numberOfVehiclesMaxAllowedForService(7)
                        .availableBookingCountForService(7)
                        .timeSlotAllocatedServiceDiscount(0.0)
                        .build(),
                AcceptedBookings.builder()
                        .key(UUID.randomUUID().toString())
                        .status("available")
                        .acceptedStatus("accepted")
                        .userEmailAddress("")
                        .timeSlotAllocatedDate("2022-01-30")
                        .timeSlotAllocatedTime("08:00")
                        .timeSlotAllocatedDuration("4 hours")
                        .timeSlotAllocatedService("Full Service Maintenance")
                        .numberOfVehiclesMaxAllowedForService(10)
                        .availableBookingCountForService(10)
                        .timeSlotAllocatedServiceDiscount(0.0)
                        .build()
        ));
        while (range > 0) {
            boolean result = airlineService.acceptTimeSlot(
                    acceptedBookings.get(range - 1)
            );
            System.out.println(result);
            Thread.sleep(1000);
            range--;
        }
        return new ResponseEntity<>(
                Map.of("message", "timeslot updated"),
                org.springframework.http.HttpStatus.OK
        );
    }

    @PostMapping("/update-timeslot")
    public ResponseEntity<Map<String, String>> addServiceBookingTimeslot() throws InterruptedException {

        Faker faker = new Faker();

        while (true) {
            boolean result = airlineService.addServiceBookingTimeslot(
                    AvailableTimeSlot.builder()
                            .key(UUID.randomUUID().toString())
                            .status("available")
                            .userEmailAddress("")
                            .timeSlotAllocatedDate(faker.date().toString())
                            .timeSlotAllocatedTime(faker.number().toString())
                            .timeSlotAllocatedDuration(faker.number().numberBetween(1, 4) + " hours")
                            .timeSlotAllocatedService(faker.commerce().productName())
                            .numberOfVehiclesMaxAllowedForService(faker.number().numberBetween(3, 10))
                            .availableBookingCountForService(faker.number().numberBetween(3, 10))
                            .timeSlotAllocatedServiceDiscount(0.0)
                            .build()
            );
            System.out.println(result);
            Thread.sleep(1000);
        }
    }

}