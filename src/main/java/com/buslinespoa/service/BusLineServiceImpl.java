package com.buslinespoa.service;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.dto.response.BusRouteResponseDTO;
import com.buslinespoa.model.BusLine;
import com.buslinespoa.model.BusRoute;
import com.buslinespoa.repository.BusLineRepository;
import com.buslinespoa.repository.BusLineSpecification;
import com.buslinespoa.repository.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("busLineService")
public class BusLineServiceImpl implements BusLineService {

	@Autowired
	private BusLineRepository busLineRepository;

	@Autowired
	private BusRouteService busRouteService;

	public BusLine findById(Long id) {
		return busLineRepository.findOne(id);
	}

	public BusLine findByName(String name) {
		return busLineRepository.findByName(name);
	}

	public BusLineDTO saveBusLine(BusLine newBusLine) {

		List<BusRoute> busRoutes = newBusLine.getBusRoutes();
		newBusLine.setBusRoutes(null);
		BusLine busLine = busLineRepository.saveAndFlush(newBusLine);
		List<BusRouteResponseDTO> busRoutesDTO = new ArrayList<>();
		if(busRoutes != null) {
			busRouteService.deleteBusRouteByBusLine(busLine);//Limpa todos itinerários
			for (BusRoute busRoute : busRoutes) {
				busRoute.setIdBusRoute(null);//Sempre irá criar itinerário novo
				busRoute.setBusLine(busLine);
				busRouteService.saveBusRoute(busRoute); //Adiciona novo ou novos itinerários
			}
		}
		busRoutesDTO = busRouteService.findAllBusRoutesByBusLine(busLine);
		BusLineDTO busLineDTO =new BusLineDTO(busLine.getIdBusLine(), busLine.getCode(),busLine.getName(),busRoutesDTO);

		return busLineDTO;
	}

	public BusLineDTO updateBusLine(BusLine newBusLine) {
		return saveBusLine(newBusLine);
	}

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

	public List<BusLine> filterBusLineByRadius(Double lat, Double lon, Double km) {

		return null;
	}


	public boolean isBusLineExist(BusLine busLine) { return findByName(busLine.getName()) != null; }

}
