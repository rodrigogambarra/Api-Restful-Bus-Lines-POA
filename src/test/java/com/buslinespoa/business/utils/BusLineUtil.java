package com.buslinespoa.business.utils;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.model.BusLine;

import java.util.Collections;

public class BusLineUtil {
	private static final String CODE = "Linha teste";
	private static final String NAME = "Ônibus teste";
	private static final long BUSLINE_ID = 1L;

	public static BusLineDTO createFakeDTO() {
		return BusLineDTO.builder()
			.code(CODE)
			.name(NAME)
			.busRoutes(Collections.singletonList(BusRouteUtil.createFakeDTO()))
			.build();
	}

	public static BusLine createFakeEntity() {
		return BusLine.builder()
			.idBusLine(BUSLINE_ID)
			.code(CODE)
			.name(NAME)
			.busRoutes(Collections.singletonList(BusRouteUtil.createFakeEntity()))
			.build();
	}
}
