package analysis.service;



import analysis.model.AvailableTimeSlots;
import analysis.repository.AvailableTimeSlotsRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AvailableTimeSlotAnalysisService {

    private static AvailableTimeSlotAnalysisService instance;
    private final AvailableTimeSlotsRepository availableTimeSlotRepository = AvailableTimeSlotsRepository.getInstance();

    public static AvailableTimeSlotAnalysisService getInstance() {
        if (instance == null) {
            instance = new AvailableTimeSlotAnalysisService();
        }
        return instance;
    }

    public Map<String, Integer> getAnalysis() throws SQLException {
        AvailableTimeSlots[] data =  availableTimeSlotRepository.getAllAvailableTimeSlots().toArray(new AvailableTimeSlots[0]);

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
