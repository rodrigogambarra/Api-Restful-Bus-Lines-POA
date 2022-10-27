package com.buslinespoa.business.services;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.model.BusLine;
import com.buslinespoa.repository.BusLineRepository;
import com.buslinespoa.service.BusLineServiceImpl;
import com.buslinespoa.service.BusRouteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static com.buslinespoa.business.utils.BusLineUtil.createFakeDTO;
import static com.buslinespoa.business.utils.BusLineUtil.createFakeEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuslineServiceTest {

	@Mock
	private BusLineRepository busLineRepository;

	@Mock
	private ModelMapper mapper;

	@Mock
	BusRouteService busRouteService;

	@InjectMocks
	private BusLineServiceImpl busLineService;

	@Test
	void testGivenValidBusLineIdThenReturnThisBusLineDTO() {
		BusLineDTO busLineDTO = createFakeDTO();
		BusLine expectedSavedBusLine = createFakeEntity();
		busLineDTO.setIdBusLine(expectedSavedBusLine.getIdBusLine());

		when(mapper.map(expectedSavedBusLine, BusLineDTO.class)).thenReturn(busLineDTO);
		when(busLineRepository.findOne(expectedSavedBusLine.getIdBusLine())).thenReturn(expectedSavedBusLine);

		BusLineDTO expectedBuslineDTO = busLineService.findById(expectedSavedBusLine.getIdBusLine());

		assertEquals(expectedSavedBusLine.getIdBusLine(), expectedBuslineDTO.getIdBusLine());
		assertEquals(expectedSavedBusLine.getName(), expectedBuslineDTO.getName());
		assertEquals(expectedSavedBusLine.getCode(), expectedBuslineDTO.getCode());
		assertEquals(expectedSavedBusLine.getBusRoutes().size(), expectedBuslineDTO.getBusRoutes().size());
	}

	@Test
	void testGivenBusLineDTOThenReturnBusLineDTO() {
		BusLineDTO busLineDTO = createFakeDTO();
		BusLine expectedSavedBusLine = createFakeEntity();
		busLineDTO.setIdBusLine(expectedSavedBusLine.getIdBusLine());

		when(mapper.map(busLineDTO, BusLine.class)).thenReturn(expectedSavedBusLine);
		when(mapper.map(expectedSavedBusLine, BusLineDTO.class)).thenReturn(busLineDTO);
		when(busLineRepository.saveAndFlush(any(BusLine.class))).thenReturn(expectedSavedBusLine);

		BusLineDTO expectedBuslineDTO = busLineService.saveBusLine(busLineDTO);

		assertEquals(expectedSavedBusLine.getIdBusLine(), expectedBuslineDTO.getIdBusLine());
		assertEquals(expectedSavedBusLine.getName(), expectedBuslineDTO.getName());
		assertEquals(expectedSavedBusLine.getCode(), expectedBuslineDTO.getCode());
		assertEquals(expectedSavedBusLine.getBusRoutes().size(), expectedBuslineDTO.getBusRoutes().size());
	}

	@Test
	void testGivenValidBusLineIdThenReturnSuccesOnDelete() {
		Long deletedBusLineId = 1L;

		busLineService.deleteBusLineById(deletedBusLineId);

		verify(busLineRepository, times(1)).delete(deletedBusLineId);

	}
}
