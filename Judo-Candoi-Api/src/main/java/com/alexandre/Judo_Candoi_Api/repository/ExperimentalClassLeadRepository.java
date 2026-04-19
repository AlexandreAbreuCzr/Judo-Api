package com.alexandre.Judo_Candoi_Api.repository;

import com.alexandre.Judo_Candoi_Api.model.ExperimentalClassLead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperimentalClassLeadRepository extends JpaRepository<ExperimentalClassLead, Long> {
    List<ExperimentalClassLead> findAllByOrderByCreatedAtDesc();
}
