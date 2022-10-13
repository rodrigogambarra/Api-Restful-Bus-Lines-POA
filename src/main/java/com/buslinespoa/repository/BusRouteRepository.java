package com.buslinespoa.repository;

import com.buslinespoa.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {

	BusRoute findOne(Long id);

	@Query(value = "select * from busroute where id_busline = ?1", nativeQuery = true)
	List<BusRoute> getBusRouteByBusLine(Long id_busline);
	@Modifying
	@Query(value = "delete from busroute where id_busline = ?1", nativeQuery = true)
	void deleteBusRouteByBusLine(Long id_busline);
}
