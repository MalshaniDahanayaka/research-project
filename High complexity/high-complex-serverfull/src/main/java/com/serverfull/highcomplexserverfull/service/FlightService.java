package com.serverfull.highcomplexserverfull.service;

import com.serverfull.highcomplexserverfull.model.AvailableTimeSlots;
import com.serverfull.highcomplexserverfull.repository.AvailableTimeSlotsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private AvailableTimeSlotsRepository availableTimeSlotsRepository;

    FlightService(AvailableTimeSlotsRepository availableTimeSlotsRepository) {
        this.availableTimeSlotsRepository = availableTimeSlotsRepository;
    }

    public List<AvailableTimeSlots> availableTimeSlots() {
        return availableTimeSlotsRepository.findAll();
    }
}
