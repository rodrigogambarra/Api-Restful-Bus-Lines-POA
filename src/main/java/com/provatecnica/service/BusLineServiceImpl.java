package com.provatecnica.service;

import com.provatecnica.model.BusLine;
import com.provatecnica.repository.BusLineRepository;
import com.provatecnica.repository.BusLineSpecification;
import com.provatecnica.repository.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("busLineService")
public class BusLineServiceImpl implements BusLineService {

	@Autowired
	private BusLineRepository busLineRepository;

	public BusLine findById(Long id) {
		return busLineRepository.findOne(id);
	}

	public BusLine findByName(String name) {
		return busLineRepository.findByName(name);
	}

	public void saveBusLine(BusLine busLine) {
		busLineRepository.save(busLine);
	}

	public void updateBusLine(BusLine busLine) { saveBusLine(busLine);	}

	public void deleteBusLineById(Long id) {
		busLineRepository.delete(id);
	}

	public void deleteAllBusLines() {
		busLineRepository.deleteAll();
	}

	public List<BusLine> findAllBusLines() {
		return busLineRepository.findAll();
	}

	public List<BusLine> filterBusLine(String name) {
		BusLineSpecification spec =
			new BusLineSpecification(new SearchCriteria("name", ":", name));
		return busLineRepository.findAll(spec);
	}

	public boolean isBusLineExist(BusLine busLine) {
		return findByName(busLine.getName()) != null;
	}

}
