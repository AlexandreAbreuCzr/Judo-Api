package com.alexandre.Judo_Candoi_Api.repository;

import com.alexandre.Judo_Candoi_Api.model.PrideStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrideStudentRepository extends JpaRepository<PrideStudent, Long> {
    List<PrideStudent> findAllByActiveTrueOrderByDisplayOrderAscIdAsc();
}
