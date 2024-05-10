package com.serverfull.highcomplexserverfull.controller;

import com.serverfull.highcomplexserverfull.model.AvailableTimeSlots;
import com.serverfull.highcomplexserverfull.service.FlightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight-booking")
public class FlightBookingController {

    private FlightService flightService;

    FlightBookingController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/")
    public List<AvailableTimeSlots> getAvailableTimeSlots() {
        return flightService.availableTimeSlots();
    }


}
