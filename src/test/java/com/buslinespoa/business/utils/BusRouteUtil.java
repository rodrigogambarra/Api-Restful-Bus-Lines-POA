package com.buslinespoa.business.utils;

import com.buslinespoa.dto.response.BusRouteResponseDTO;
import com.buslinespoa.model.BusRoute;


public class BusRouteUtil {
	private static final Double LATITUDE = -30.147212063109;
	private static final Double LONGITUDE = -51.214982284567;

	private static final long BUSROUTE_ID = 1L;

	public static BusRouteResponseDTO createFakeDTO() {
		return BusRouteResponseDTO.builder()
			.latitude(LATITUDE)
			.longitude(LONGITUDE)
			.build();
	}

	public static BusRoute createFakeEntity() {
		return BusRoute.builder()
			.idBusRoute(BUSROUTE_ID)
			.latitude(LATITUDE)
			.longitude(LONGITUDE)
			.build();
	}
}
