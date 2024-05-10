package com.serverfull.highcomplexserverfull.service;

import com.serverfull.highcomplexserverfull.model.AvailableTimeSlots;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AvailableTimeSlotAnalyzer {

    private FlightService flightService;

    public AvailableTimeSlotAnalyzer(FlightService flightService) {
        this.flightService = flightService;
    }

    public Map<String, Integer>  analyzeTimeSlots() {
        AvailableTimeSlots[] data = flightService.availableTimeSlots().toArray(new AvailableTimeSlots[0]);

        // User Analysis - Number of Bookings per User
        Map<String, Integer> userBookingCount = new HashMap<>();
        for (AvailableTimeSlots slot : data) {
            String userEmail = slot.getUserEmailAddress();
            userBookingCount.put(userEmail, userBookingCount.getOrDefault(userEmail, 0) + 1);
        }

        // Print Top 5 Users with most bookings (example)
        for (int i = 0; i < 5; i++) {
            String user = findMaxKey(userBookingCount);
            if (user == null) {
                break;
            }
            System.out.println("User: " + user + ", Bookings: " + userBookingCount.get(user));
            userBookingCount.remove(user);
        }

        // Service Analysis - Most Popular Service
        Map<String, Integer> serviceCount = new HashMap<>();
        for (AvailableTimeSlots slot : data) {
            String service = slot.getTimeSlotAllocatedService();
            serviceCount.put(service, serviceCount.getOrDefault(service, 0) + 1);
        }

        // Print Top 5 Most Popular Services (example)
        for (int i = 0; i < 5; i++) {
            String service = findMaxKey(serviceCount);
            if (service == null) {
                break;
            }
            System.out.println("Service: " + service + ", Count: " + serviceCount.get(service));
            serviceCount.remove(service);
        }
        return serviceCount;
    }

    private static <K, V extends Comparable<V>> String findMaxKey(Map<K, V> map) {
        K maxKey = null;
        V maxValue = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (maxValue == null || entry.getValue().compareTo(maxValue) > 0) {
                maxKey = entry.getKey();
                maxValue = entry.getValue();
            }
        }
        return (String) maxKey;
    }

}
