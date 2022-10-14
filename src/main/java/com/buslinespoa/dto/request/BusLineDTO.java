package com.buslinespoa.dto.request;

import com.buslinespoa.dto.response.BusRouteResponseDTO;
import com.buslinespoa.model.BusLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class BusLineDTO {

	private Long idBusLine;
	@NotEmpty
	private String code;
	@NotEmpty
	private String name;
	@Valid
	private List<BusRouteResponseDTO> busRoutes;

	public BusLineDTO(BusLine busLine){
		this.idBusLine = busLine.getIdBusLine();
		this.code = busLine.getCode();
		this.name = busLine.getName();
		this.busRoutes = new ArrayList<>();
	}
}
