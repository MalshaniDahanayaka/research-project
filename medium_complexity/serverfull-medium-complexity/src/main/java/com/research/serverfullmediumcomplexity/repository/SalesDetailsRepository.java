package com.research.serverfullmediumcomplexity.repository;

import com.research.serverfullmediumcomplexity.model.SalesDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesDetailsRepository extends JpaRepository<SalesDetail, Long> {
}
