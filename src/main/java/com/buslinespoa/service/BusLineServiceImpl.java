package com.buslinespoa.service;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.model.BusLine;
import com.buslinespoa.model.BusRoute;
import com.buslinespoa.model.Spot;
import com.buslinespoa.repository.BusLineRepository;
import com.buslinespoa.repository.BusLineSpecification;
import com.buslinespoa.repository.SearchCriteria;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("busLineService")
public class BusLineServiceImpl implements BusLineService {

	@Autowired
	private BusLineRepository busLineRepository;

	@Autowired
	private BusRouteService busRouteService;

	@Autowired
	private ModelMapper mapper;

	public BusLineDTO findById(Long id) {
		BusLine busLine = busLineRepository.findOne(id);
		BusLineDTO busLineDTO = null;
		if(busLine != null)
			busLineDTO = mapper.map(busLine,BusLineDTO.class);
		return busLineDTO;
	}

	public BusLine findByName(String name) {
		return busLineRepository.findByName(name);
	}

	public BusLineDTO saveBusLine(BusLineDTO busLineDTO) {
		BusLine busLine = mapper.map(busLineDTO, BusLine.class);
		List<BusRoute> busRoutes = busLine.getBusRoutes();
		BusLine newBusLine = busLineRepository.saveAndFlush(busLine);
		busRouteService.deleteBusRouteByBusLine(newBusLine);//Limpa todos itinerários
		if(busRoutes != null) {
			for (BusRoute busRoute : busRoutes) {
				busRoute.setIdBusRoute(null);//Sempre irá criar itinerário novo
				busRoute.setBusLine(newBusLine);
				busRouteService.saveBusRoute(busRoute); //Adiciona novo ou novos itinerários
			}
			newBusLine.setBusRoutes(busRoutes);
		}
		busLineDTO = mapper.map(newBusLine, BusLineDTO.class);
		return busLineDTO;
	}

	public BusLineDTO updateBusLine(BusLineDTO newBusLineDTO) {
		return saveBusLine(newBusLineDTO);
	}

	public void deleteBusLineById(Long id) {
		busLineRepository.delete(id);
	}

	public void deleteAllBusLines() {
		busLineRepository.deleteAll();
	}

	public List<BusLineDTO> findAllBusLines() {
		List<BusLine> busLines = busLineRepository.findAll();
		List<BusLineDTO> busLineDTOS = new ArrayList<>();
		for (BusLine busLine: busLines) {
			BusLineDTO busLineDTO = mapper.map(busLine, BusLineDTO.class);
			busLineDTOS.add(busLineDTO);
		}
		return busLineDTOS;
	}

	public List<BusLine> filterBusLine(String name) {
		BusLineSpecification spec =
			new BusLineSpecification(new SearchCriteria("name", ":", name));
		return busLineRepository.findAll(spec);
	}

	public List<BusLineDTO> filterBusLineByRadius(Double lat, Double lon, Double km) {
		List<BusLine> buslines = busLineRepository.filterBusLineRadius(lat, lon, km);

		return buslines.stream().map(objetos -> mapper.map(objetos, BusLineDTO.class))
			.collect(Collectors.toList());
	}

	public List<BusLineDTO> filterBusLineRadius(Spot spot) {
		Double lat = spot.getLatitude();
		Double lon = spot.getLongitude();
		Double km = spot.getKm();
		return filterBusLineByRadius(lat, lon, km);
	}

	public boolean isBusLineExist(BusLine busLine) { return findByName(busLine.getName()) != null; }

}
