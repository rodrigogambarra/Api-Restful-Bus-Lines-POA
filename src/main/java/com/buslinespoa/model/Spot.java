package com.buslinespoa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spot {

	@NotNull
	private Double latitude;
	@NotNull
	private Double longitude;
	@NotNull
	private Double km;
}
