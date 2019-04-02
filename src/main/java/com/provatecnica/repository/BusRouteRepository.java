package com.provatecnica.repository;

import com.provatecnica.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {

	BusRoute findOne(Long id);
}
