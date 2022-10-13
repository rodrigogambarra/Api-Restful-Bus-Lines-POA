package com.buslinespoa.repository;

import com.buslinespoa.model.BusLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusLineRepository extends JpaRepository<BusLine, Long>, JpaSpecificationExecutor<BusLine> {

	BusLine findOne(Long id);

	BusLine findByName(String name);

	@Query(value = "SELECT BL.ID_BUSLINE, BL.CODE, BL.NAME, T.DISTANCE\n" +
		"FROM (SELECT TAB.ID_BUSLINE, MIN(TAB.DISTANCE) AS DISTANCE\n" +
		"        FROM (SELECT BUS.ID_BUSROUTE, BUS.ID_BUSLINE, BUS.LATITUDE, BUS.LONGITUDE,\n" +
		"                    (6371 * acos(\n" +
		"                        cos(radians(?1)) * cos(radians(BUS.LATITUDE)) *\n" +
		"                        cos(radians(?2) - radians(BUS.LONGITUDE)) +\n" +
		"                        sin(radians(?1)) * sin(radians(BUS.LATITUDE))\n" +
		"                    )) AS DISTANCE\n" +
		"                FROM BUSROUTE BUS) TAB\n" +
		"        WHERE TAB.DISTANCE < ?3\n" +
		"        GROUP BY TAB.ID_BUSLINE\n" +
		"        ) T,\n" +
		"    BUSLINE BL\n" +
		"WHERE BL.ID_BUSLINE = T.ID_BUSLINE\n" +
		"ORDER BY T.DISTANCE, BL.NAME, BL.CODE", nativeQuery=true)
	List<BusLine> filterBusLineRadius(Double lat, Double lon, Double km);

}
