package timeslots.repository;

import timeslots.db.DatabaseConnection;
import timeslots.model.AvailableTimeSlots;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvailableTimeSlotsRepository {

    private static AvailableTimeSlotsRepository instance = null;
    private DatabaseConnection connection = DatabaseConnection.getInstance();

    public static AvailableTimeSlotsRepository getInstance() {
        if (instance == null) {
            instance = new AvailableTimeSlotsRepository();
        }
        return instance;
    }


    public List<AvailableTimeSlots> getAllAvailableTimeSlots() throws SQLException {
        List<AvailableTimeSlots> response = new ArrayList<>();
        String query = "SELECT * FROM available_time_slots";
        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                AvailableTimeSlots availableTimeSlot = new AvailableTimeSlots();
                availableTimeSlot.setId(resultSet.getLong("id"));
                availableTimeSlot.setKey(resultSet.getString("key"));
                availableTimeSlot.setStatus(resultSet.getString("status"));
                availableTimeSlot.setUserEmailAddress(resultSet.getString("user_email_address"));
                availableTimeSlot.setTimeSlotAllocatedDate(resultSet.getString("time_slot_allocated_date"));
                availableTimeSlot.setTimeSlotAllocatedTime(resultSet.getString("time_slot_allocated_time"));
                availableTimeSlot.setTimeSlotAllocatedDuration(resultSet.getString("time_slot_allocated_duration"));
                availableTimeSlot.setTimeSlotAllocatedService(resultSet.getString("time_slot_allocated_service"));
                availableTimeSlot.setNumberOfVehiclesMaxAllowedForService(resultSet.getInt("number_of_vehicles_max_allowed_for_service"));
                availableTimeSlot.setAvailableBookingCountForService(resultSet.getInt("available_booking_count_for_service"));
                availableTimeSlot.setTimeSlotAllocatedServiceDiscount(resultSet.getDouble("time_slot_allocated_service_discount"));
                response.add(availableTimeSlot);
            }
        }
        return response;
    }
}
