package com.serverfull.highcomplexserverfull.repository;

import com.serverfull.highcomplexserverfull.dto.AcceptedBookings;
import com.serverfull.highcomplexserverfull.model.AcceptedBookingTimeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptedBookingRepository extends JpaRepository<AcceptedBookingTimeslot, Long> {
}
