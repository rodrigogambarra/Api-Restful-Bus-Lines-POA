package com.buslinespoa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusRouteDTO {
	private Long id;
	private Double latitude;
	private Double longitude;
	private BusLineDTO busLineDTO;
}
