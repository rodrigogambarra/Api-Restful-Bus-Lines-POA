package com.buslinespoa.dto.response;

import com.buslinespoa.model.BusRoute;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class BusRouteResponseDTO {
	private Long id;
	@NotNull
	private Double latitude;
	@NotNull
	private Double longitude;

	public BusRouteResponseDTO(BusRoute busRoute){
		this.id = busRoute.getIdBusRoute();
		this.latitude = busRoute.getLatitude();
		this.longitude = busRoute.getLongitude();
	}
	public BusRouteResponseDTO(Long id, Double latitude, Double longitude){
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
