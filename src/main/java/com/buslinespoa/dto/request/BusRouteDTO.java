package com.buslinespoa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRouteDTO {
	private Long id;
	private Double latitude;
	private Double longitude;
	private BusLineDTO busLineDTO;
}
