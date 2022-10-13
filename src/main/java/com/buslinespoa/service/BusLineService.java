package com.buslinespoa.service;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.model.BusLine;

import java.util.List;

public interface BusLineService {

	BusLine findById(Long id);

	BusLine findByName(String name);

	BusLineDTO saveBusLine(BusLine linha);

	BusLineDTO updateBusLine(BusLine linha);

	void deleteBusLineById(Long id);

	void deleteAllBusLines();

	List<BusLine> findAllBusLines();

	List<BusLine> filterBusLine(String name);

	boolean isBusLineExist(BusLine linha);
}
