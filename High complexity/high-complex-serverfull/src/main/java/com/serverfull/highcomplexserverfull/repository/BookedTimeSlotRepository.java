package com.serverfull.highcomplexserverfull.repository;

import com.serverfull.highcomplexserverfull.model.BookedTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedTimeSlotRepository extends JpaRepository<BookedTimeSlot, Long> {

    void deleteByKey(String key);
}
