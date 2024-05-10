package com.serverfull.highcomplexserverfull.controller;

import com.serverfull.highcomplexserverfull.service.AvailableTimeSlotAnalyzer;
import com.serverfull.highcomplexserverfull.service.FlightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/analyser")
@RestController
public class Analyser {

    private AvailableTimeSlotAnalyzer availableTimeSlotAnalyzer;

    Analyser(AvailableTimeSlotAnalyzer availableTimeSlotAnalyzer) {
        this.availableTimeSlotAnalyzer = availableTimeSlotAnalyzer;
    }

    @GetMapping("/")
    public Map<String, Integer> getAnalyser() {
        return availableTimeSlotAnalyzer.analyzeTimeSlots();
    }

}
