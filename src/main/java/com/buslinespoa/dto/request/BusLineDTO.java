package com.buslinespoa.dto.request;

import com.buslinespoa.dto.response.BusRouteResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusLineDTO {

	private Long idBusLine;
	private String code;
	private String name;
	private List<BusRouteResponseDTO> busRoutes;
}
