package com.buslinespoa.service;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.model.BusLine;
import com.buslinespoa.model.Spot;

import java.util.List;

public interface BusLineService {

	BusLineDTO findById(Long id);

	BusLine findByName(String name);

	BusLineDTO saveBusLine(BusLineDTO busLineDTO);

	BusLineDTO updateBusLine(BusLineDTO busLineDTO);

	void deleteBusLineById(Long id);

	void deleteAllBusLines();

	List<BusLineDTO> findAllBusLines();

	List<BusLine> filterBusLine(String name);

	List<BusLineDTO> filterBusLineByRadius(Double lat, Double lon, Double km);

	List<BusLineDTO> filterBusLineRadius(Spot spot);

	boolean isBusLineExist(BusLine linha);
}
