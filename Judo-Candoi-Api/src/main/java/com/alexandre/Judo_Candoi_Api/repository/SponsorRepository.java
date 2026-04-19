package com.alexandre.Judo_Candoi_Api.repository;

import com.alexandre.Judo_Candoi_Api.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    List<Sponsor> findAllByActiveTrueOrderByDisplayOrderAscIdAsc();
}
