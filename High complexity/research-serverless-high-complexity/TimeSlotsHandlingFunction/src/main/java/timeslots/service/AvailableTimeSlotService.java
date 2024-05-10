package timeslots.service;

import timeslots.model.AvailableTimeSlots;
import timeslots.repository.AvailableTimeSlotsRepository;

import java.sql.SQLException;
import java.util.List;

public class AvailableTimeSlotService {

    private static AvailableTimeSlotService instance;
    private final AvailableTimeSlotsRepository availableTimeSlotRepository = AvailableTimeSlotsRepository.getInstance();

    public static AvailableTimeSlotService getInstance() {
        if (instance == null) {
            instance = new AvailableTimeSlotService();
        }
        return instance;
    }

    public List<AvailableTimeSlots> getAllAvailableTimeSlots() throws SQLException {
        return availableTimeSlotRepository.getAllAvailableTimeSlots();
    }

}
