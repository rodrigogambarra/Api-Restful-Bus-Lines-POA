package com.buslinespoa.dto.response;

import com.buslinespoa.model.BusRoute;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusRouteResponseDTO {
	private Long id;
	private Double latitude;
	private Double longitude;

	public BusRouteResponseDTO(BusRoute busRoute){
		this.id = busRoute.getIdBusRoute();
		this.latitude = busRoute.getLatitude();
		this.longitude = busRoute.getLongitude();
	}
}
