package com.provatecnica.controller;

import com.provatecnica.model.BusLine;
import com.provatecnica.service.BusLineService;
import com.provatecnica.util.CustomErrorType;
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

@Api(value = "API REST Prova Tecnica")
@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	BusLineService busLineService;

	@ApiOperation(value = "Return All BusLines")
	@RequestMapping(value = "/busLine/", method = RequestMethod.GET)
	public ResponseEntity<List<BusLine>> listAllBusLines() {
		List<BusLine> busLines = busLineService.findAllBusLines();
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

	@ApiOperation(value = "Fetch a BusLine with idBusLine")
	@RequestMapping(value = "/busLine/{idBusLine}", method = RequestMethod.GET)
	public ResponseEntity<?> getBusLine(@PathVariable("idBusLine") long idBusLine) {
		logger.info("Fetching BusLine with idBusLine {}", idBusLine);
		BusLine busLine = busLineService.findById(idBusLine);
		if (busLine == null) {
			logger.error("BusLine with idBusLine {} not found.", idBusLine);
			return new ResponseEntity(new CustomErrorType("BusLine with idBusLine " + idBusLine + " not found"), HttpStatus.NOT_FOUND);
		}
		busLine.add(linkTo(methodOn(RestApiController.class).listAllBusLines()).withRel("BusLine list"));
		return new ResponseEntity<BusLine>(busLine, HttpStatus.OK);
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
	public ResponseEntity<?> deleteBusLine(@Valid @RequestBody BusLine busLine){
		logger.info("Searching BusLine with idBusLine {}", busLine.getIdBusLine());
		BusLine busLine1 = busLineService.findById(busLine.getIdBusLine());
		if(busLine1 == null){
			logger.error("BusLine with idBusLine {} not found.", busLine.getIdBusLine());
			return new ResponseEntity(new CustomErrorType("BusLine with idBusLine " + busLine.getIdBusLine() + " not found"), HttpStatus.NOT_FOUND);
		}
		busLineService.deleteBusLineById(busLine.getIdBusLine());
		return new ResponseEntity<BusLine>(busLine, HttpStatus.OK);
	}

	@ApiOperation(value = "Create or update a BusLine")
	@RequestMapping(value = "/busLine", method = RequestMethod.POST)
	public ResponseEntity<?> postBusLine(@Valid @RequestBody BusLine newBusLine) {

		BusLine busLine = busLineService.saveBusLine(newBusLine);
		if (busLine == null) {
			logger.info("Creating BusLine with name {}", newBusLine.getName());
			busLineService.saveBusLine(newBusLine);
		}
		else {
			logger.info("Updating BusLine with name {}", newBusLine.getName());
			busLineService.updateBusLine(newBusLine);
		}
		busLine.add(linkTo(methodOn(RestApiController.class).getBusLine(busLine.getIdBusLine())).withSelfRel());

		return new ResponseEntity<BusLine>(busLine, HttpStatus.CREATED);
	}
}
