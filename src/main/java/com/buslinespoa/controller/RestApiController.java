package com.buslinespoa.controller;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.model.BusLine;
import com.buslinespoa.model.Spot;
import com.buslinespoa.service.BusLineService;
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

	@ApiOperation(value = "Return All BusLines")
	@RequestMapping(value = "/busLine/", method = RequestMethod.GET)
	public ResponseEntity<List<BusLineDTO>> listAllBusLines() {
		List<BusLineDTO> busLinesDTO = busLineService.findAllBusLines();
		if (busLinesDTO.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
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
		return new ResponseEntity<List<BusLine>>(busLines, HttpStatus.OK);
	}

	@ApiOperation(value = "Update a BusLine with idBusLine")
	@RequestMapping(value = "/busLine/{idBusLine}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBusLine(@PathVariable("idBusLine") long idBusLine, @Valid @RequestBody BusLineDTO busLine) {
		logger.info("Updating BusLine with idBusLine {}", idBusLine);
		BusLineDTO currentBusLine = busLineService.findById(idBusLine);
		if (currentBusLine == null) {
			logger.error("Unable to update. BusLine with idBusLine {} not found.", idBusLine);
			return new ResponseEntity(
				new CustomErrorType("Unable to upate. BusLine with idBusLine " + idBusLine + " not found."), HttpStatus.NOT_FOUND);
		}
		currentBusLine.setName(busLine.getName());
		currentBusLine.setCode(busLine.getCode());
		currentBusLine.setBusRoutes(busLine.getBusRoutes());
		busLineService.updateBusLine(currentBusLine);
		return new ResponseEntity<BusLineDTO>(currentBusLine, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a BusLine")
	@RequestMapping(value = "/busLine/{idBusLine}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBusLine(@PathVariable("idBusLine") long idBusLine){
		logger.info("Searching BusLine with idBusLine {}", idBusLine);
		BusLineDTO busLineDTO = busLineService.findById(idBusLine);
		if(busLineDTO == null){
			logger.error("BusLine with idBusLine {} not found.", idBusLine);
			return new ResponseEntity(new CustomErrorType("BusLine with idBusLine " + idBusLine + " not found"), HttpStatus.NOT_FOUND);
		}
		logger.info("BusLine with idBusLine {} deleted.", idBusLine);
		busLineService.deleteBusLineById(idBusLine);
		return new ResponseEntity(new CustomSucessType("BusLine with idBusLine "+ idBusLine + " deleted"), HttpStatus.OK);
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
		return new ResponseEntity<BusLineDTO>(busLineDTO, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Fetch a BusLine list in km radius using attributes")
	@RequestMapping(value = "/filterBusLineByRadius/", method = RequestMethod.GET)
	public ResponseEntity<?> getBusLineFilterByRadius(@RequestParam Double latitude,@RequestParam Double longitude, @RequestParam Double km) {
		logger.info("Fetching BusLine with");
		List<BusLineDTO> busLines = busLineService.filterBusLineByRadius(latitude, longitude, km);
		if (busLines.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		for (BusLineDTO busLine : busLines) {
			Long idBusLine = busLine.getIdBusLine();
			busLine.setBusRoutes(null);
		}
		return new ResponseEntity<>(busLines, HttpStatus.OK);
	}

	@ApiOperation(value = "Fetch a BusLine list in km radius using param spot")
	@RequestMapping(value = "/FilterBusLineByRadius/", method = RequestMethod.POST)
	public ResponseEntity<?> BusLineFilterByRadius(@Valid @RequestBody Spot spot) {
		logger.info("Fetching BusLine with");
		List<BusLineDTO> busLines = busLineService.filterBusLineRadius(spot);
		if (busLines.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		for (BusLineDTO busLine : busLines) {
			Long idBusLine = busLine.getIdBusLine();
			busLine.setBusRoutes(null);
		}
		return new ResponseEntity<List<BusLineDTO>>(busLines, HttpStatus.OK);
	}
}
