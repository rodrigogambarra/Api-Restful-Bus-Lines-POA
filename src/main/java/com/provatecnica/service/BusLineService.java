package com.provatecnica.service;

import com.provatecnica.model.BusLine;

import java.util.List;

public interface BusLineService {

	BusLine findById(Long id);

	BusLine findByName(String name);

	void saveBusLine(BusLine linha);

	void updateBusLine(BusLine linha);

	void deleteBusLineById(Long id);

	void deleteAllBusLines();

	List<BusLine> findAllBusLines();

	List<BusLine> filterBusLine(String name);

	boolean isBusLineExist(BusLine linha);
}
