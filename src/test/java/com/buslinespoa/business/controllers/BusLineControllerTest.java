package com.buslinespoa.business.controllers;

import com.buslinespoa.controller.RestApiController;
import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.repository.BusLineRepository;
import com.buslinespoa.service.BusLineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.List;

import static com.buslinespoa.business.utils.BusLineUtil.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
public class BusLineControllerTest {
	private static final String BUSLINE_API_URL_PATH = "/api/";

	private MockMvc mockMvc;

	private RestApiController restApiController;

	@Mock
	private BusLineRepository busLineRepository;

	@Mock
	private ModelMapper mapper;
	@Mock
	private BusLineService busLineService;

	@BeforeEach
	void setUp() {
		restApiController = new RestApiController(busLineService);
		mockMvc = MockMvcBuilders.standaloneSetup(restApiController)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
			.build();
	}
	@Test
	void testWhenPOSTIsCalledThenABusLinehouldBeCreated() throws Exception {
		BusLineDTO busLineDTO = createFakeDTO();
		BusLineDTO expectedBusLineDTO = createFakeDTOWithId();

		when(busLineService.updateBusLine(busLineDTO)).thenReturn(expectedBusLineDTO);

		mockMvc.perform(post(BUSLINE_API_URL_PATH + "/busLine/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(busLineDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.idBusLine", is(1)))
				.andExpect(jsonPath("$.code", is("Linha teste")))
				.andExpect(jsonPath("$.name", is("Ônibus teste")));
	}

	@Test
	void testWhenDELETEIsCalledThenABuslineNotFound() throws Exception {
		Long expectedValidId = 1L;
		mockMvc.perform(delete(BUSLINE_API_URL_PATH + "/busLine/" + expectedValidId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testWhenDELETEIsCalledThenABuslineShouldBeDeleted() throws Exception {
		Long expectedValidId = 1L;
		BusLineDTO expectedBusLineDTO = createFakeDTOWithId();

		when(busLineService.findById(expectedBusLineDTO.getIdBusLine())).thenReturn(expectedBusLineDTO);

		mockMvc.perform(delete(BUSLINE_API_URL_PATH + "/busLine/" + expectedValidId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	void testWhenGETWithValidIsCalledThenBuslineShouldBeReturned() throws Exception {
		Long expectedValidId = 1L;
		BusLineDTO expectedBusLineDTO = createFakeDTOWithId();
		when(busLineService.findById(expectedBusLineDTO.getIdBusLine())).thenReturn(expectedBusLineDTO);
		mockMvc.perform(get(BUSLINE_API_URL_PATH + "/busLine/" + expectedValidId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	void testWhenGETWithInvalidIsCalledThenNotFound() throws Exception {
		Long expectedInvalidId = 1L;
			mockMvc.perform(get(BUSLINE_API_URL_PATH + "/busLine/" + expectedInvalidId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

	@Test
	void testWhenGETIsCalledThenBuslineListShouldBeReturned() throws Exception {
		BusLineDTO expectedBusLineDTO = createFakeDTOWithId();
		List<BusLineDTO> expectedBusLineDTOList = Collections.singletonList(expectedBusLineDTO);
		when(busLineService.findAllBusLines()).thenReturn(expectedBusLineDTOList);
		mockMvc.perform(get(BUSLINE_API_URL_PATH + "/busLine/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idBusLine", is(1)))
				.andExpect(jsonPath("$[0].code", is("Linha teste")))
				.andExpect(jsonPath("$[0].name", is("Ônibus teste")));
	}

}
