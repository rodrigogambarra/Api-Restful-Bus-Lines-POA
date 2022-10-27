package com.buslinespoa.business.utils;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.model.BusLine;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;

public class BusLineUtil {
	private static final String CODE = "Linha teste";
	private static final String NAME = "Ônibus teste";
	private static final long BUSLINE_ID = 1L;

	public static BusLineDTO createFakeDTOWithId() {
		return BusLineDTO.builder()
			.idBusLine(BUSLINE_ID)
			.code(CODE)
			.name(NAME)
			.busRoutes(Collections.singletonList(BusRouteUtil.createFakeDTO()))
			.build();
	}

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
	public static String asJsonString(BusLineDTO buslineDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			objectMapper.registerModules(new JavaTimeModule());

			return objectMapper.writeValueAsString(buslineDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
