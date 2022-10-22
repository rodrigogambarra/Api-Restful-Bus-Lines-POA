package com.buslinespoa.service;

import com.buslinespoa.dto.response.BusRouteResponseDTO;
import com.buslinespoa.model.BusLine;
import com.buslinespoa.model.BusRoute;
import com.buslinespoa.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusRouteService {
	@Autowired
	private BusRouteRepository busRouteRepository;

	public BusRoute findById(Long id) {
		return busRouteRepository.findOne(id);
	}

	public void saveBusRoute(BusRoute busRoute) {
		busRouteRepository.saveAndFlush(busRoute);
	}

	public void updateBusRoute(BusRoute busRoute) {
		saveBusRoute(busRoute);
	}

	public void deleteBusRouteById(Long id) {
		busRouteRepository.delete(id);
	}

	public void deleteAllBusRoutes() {
		busRouteRepository.deleteAll();
	}

	public List<BusRoute> findAllBusRoutes() {
		return busRouteRepository.findAll();
	}
	public void deleteBusRouteByBusLine(BusLine busLine){
		try {
			List<BusRoute> busRoutes = busRouteRepository.getBusRouteByBusLine(busLine.getIdBusLine());
			if(busRoutes != null) {
				for(BusRoute busRoute : busRoutes)
					busRouteRepository.delete(busRoute.getIdBusRoute());
			}
		}
		catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	public List<BusRouteResponseDTO> findAllBusRoutesByBusLine(BusLine busLine) {
		List<BusRoute> busRoutes = busRouteRepository.getBusRouteByBusLine(busLine.getIdBusLine());
		List<BusRouteResponseDTO> busRouteDTOS = new ArrayList<>();
		for(BusRoute busRoute : busRoutes){
			busRouteDTOS.add(new BusRouteResponseDTO(busRoute));
		}
		return busRouteDTOS;
	}
}
