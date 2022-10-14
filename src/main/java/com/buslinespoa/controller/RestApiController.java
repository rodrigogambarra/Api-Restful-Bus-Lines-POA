package com.buslinespoa.controller;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.model.BusLine;
import com.buslinespoa.service.BusLineService;
import com.buslinespoa.service.BusRouteService;
import com.buslinespoa.util.CustomErrorType;
import com.buslinespoa.util.CustomSucessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(value = "API REST Bus Lines Poa")
@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	BusLineService busLineService;

	@Autowired
	BusRouteService busRouteService;

	@ApiOperation(value = "Return All BusLines")
	@RequestMapping(value = "/busLine/", method = RequestMethod.GET)
	public ResponseEntity<List<BusLineDTO>> listAllBusLines() {
		List<BusLineDTO> busLinesDTO = busLineService.findAllBusLines();
		if (busLinesDTO.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		/*for (BusLineDTO busLineDTO : busLinesDTO) {
			Long idBusLine = busLineDTO.getIdBusLine();
			busLineDTO.add(linkTo(methodOn(RestApiController.class).getBusLine(idBusLine)).withSelfRel());
		}*/
		return new ResponseEntity<>(busLinesDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Fetch a BusLine with idBusLine")
	@RequestMapping(value = "/busLine/{idBusLine}", method = RequestMethod.GET)
	public ResponseEntity<?> getBusLine(@PathVariable("idBusLine") long idBusLine) {
		logger.info("Fetching BusLine with idBusLine {}", idBusLine);
		BusLineDTO busLineDTO = busLineService.findById(idBusLine);
		if (busLineDTO == null) {
			logger.error("BusLine with idBusLine {} not found.", idBusLine);
			return new ResponseEntity(new CustomErrorType("BusLine with idBusLine " + idBusLine + " not found"), HttpStatus.NOT_FOUND);
		}
		//busLineDTO.add(linkTo(methodOn(RestApiController.class).listAllBusLines()).withRel("BusLine list"));
		return new ResponseEntity<BusLineDTO>(busLineDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Fetch a BusLine with name")
	@RequestMapping(value = "/FilterBusLine/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getBusLineForName(@PathVariable("name") String name) {
		logger.info("Fetching BusLine with name {}", name);
		List<BusLine> busLines = busLineService.filterBusLine(name);
		if (busLines.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		for (BusLine busLine : busLines) {
			Long idBusLine = busLine.getIdBusLine();
			busLine.add(linkTo(methodOn(RestApiController.class).getBusLine(idBusLine)).withSelfRel());
			busLine.setBusRoutes(null);
		}
		return new ResponseEntity<List<BusLine>>(busLines, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a BusLine")
	@RequestMapping(value = "/busLine", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBusLine(@Valid @RequestBody BusLineDTO busLine){

		if(busLine.getIdBusLine() == null){
			return new ResponseEntity(new CustomErrorType("idBusLine uninformed"), HttpStatus.BAD_REQUEST);
		}
		logger.info("Searching BusLine with idBusLine {}", busLine.getIdBusLine());
		BusLineDTO busLineDTO = busLineService.findById(busLine.getIdBusLine());
		if(busLineDTO == null){
			logger.error("BusLine with idBusLine {} not found.", busLine.getIdBusLine());
			return new ResponseEntity(new CustomErrorType("BusLine with idBusLine " + busLine.getIdBusLine() + " not found"), HttpStatus.NOT_FOUND);
		}
		logger.info("BusLine with idBusLine {} deleted.", busLine.getIdBusLine());
		busLineService.deleteBusLineById(busLine.getIdBusLine());
		return new ResponseEntity(new CustomSucessType("BusLine with idBusLine "+ busLine.getIdBusLine() + " deleted"), HttpStatus.OK);
	}

	@ApiOperation(value = "Create or update a BusLine")
	@RequestMapping(value = "/busLine", method = RequestMethod.POST)
	public ResponseEntity<?> postBusLine(@Valid @RequestBody BusLineDTO newBusLine) {

		BusLineDTO busLineDTO = null;
		if(newBusLine.getIdBusLine() != null) {
			busLineDTO = busLineService.findById(newBusLine.getIdBusLine());
		}
		if (busLineDTO == null) {
			logger.info("Creating BusLine with name {}", newBusLine.getName());
		}else {
			logger.info("Updating BusLine with name {}", newBusLine.getName());
		}
		busLineDTO = busLineService.updateBusLine(newBusLine);
		//busLineDTO.add(linkTo(methodOn(RestApiController.class).getBusLine(busLineDTO.getId())).withSelfRel());
		return new ResponseEntity<BusLineDTO>(busLineDTO, HttpStatus.CREATED);
	}
}
