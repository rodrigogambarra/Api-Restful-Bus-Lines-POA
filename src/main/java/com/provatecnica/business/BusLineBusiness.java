package com.provatecnica.business;

import com.provatecnica.controller.RestApiController;
import com.provatecnica.model.BusLine;
import com.provatecnica.repository.BusLineRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BusLineBusiness {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	BusLineRepository busLineRepository;

	@ApiOperation(value = "Fech a BusLine list in km radius")
	@RequestMapping(value = "/FilterBusLineByRadius/", method = RequestMethod.GET)
	public ResponseEntity<?> getBusLineForName(@RequestParam Double lat,@RequestParam Double lon, @RequestParam Double km) {
		logger.info("Fetching BusLine with");
		List<BusLine> busLines = busLineRepository.filterBusLineRadius(lat, lon, km);
		if (busLines.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BusLine>>(busLines, HttpStatus.OK);
	}

}
