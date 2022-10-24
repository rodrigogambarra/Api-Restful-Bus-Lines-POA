package com.buslinespoa.configuration;

import com.buslinespoa.dto.request.BusLineDTO;
import com.buslinespoa.dto.response.BusRouteResponseDTO;
import com.buslinespoa.service.BusLineService;
import com.buslinespoa.service.BusRouteService;
import com.google.gson.*;
import com.buslinespoa.model.BusLine;
import com.buslinespoa.model.BusRoute;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private BusRouteService busRouteService;

	@Autowired
	private BusLineService busLineService;

	@Autowired
	private ModelMapper mapper;

	public void run(ApplicationArguments args) throws Exception {

		//loadBusLineToDataBase();
		File file = new File("/tmp/onibus/");
		if (!file.exists()) {
			file.mkdir();
			saveBusLineToDisk();
			loadBusLineFromFile();
		}else loadBusLineFromFile();
	}

	private void loadBusLineToDataBase() throws Exception {

		HttpClient httpClient = HttpClientBuilder.create().build();
		Gson g = new Gson();
		List<BusLineDTO> busLines = new ArrayList<>();
		List<BusRouteResponseDTO> busRoutes = null;
		String url = null;
		HttpGet request = null;
		HttpResponse response = null;
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject;
		JsonObject obj;
		JsonArray jsonArray;
		FileWriter file = null;

		url = "http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=&t=o";
		request = new HttpGet(url);
		response = httpClient.execute(request);
		if (response != null) {
			InputStream source = response.getEntity().getContent(); //Get the data in the entity
			Reader reader = new InputStreamReader(source);
			jsonArray = (JsonArray) jsonParser.parse(reader);
			reader.close();
			source.close();

			System.out.println("CARREGANDO BASE DE DADOS ...");
			for (JsonElement lin: jsonArray) {
				jsonObject = lin.getAsJsonObject();
				BusLineDTO busLine = new BusLineDTO(
					Long.parseLong(jsonObject.get("id").toString().replace("\"", "")),
					jsonObject.get("codigo").toString().replace("\"", ""),
					jsonObject.get("nome").toString().replace("\"", ""));

				url = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=" + busLine.getId();
				request = new HttpGet(url);
				response = httpClient.execute(request);
				if (response != null) {
					source = response.getEntity().getContent(); //Get the data in the entity
					reader = new InputStreamReader(source);
					jsonObject = (JsonObject) jsonParser.parse(reader);
					reader.close();
					source.close();

					busRoutes = new ArrayList<>();
					BusRouteResponseDTO busRoute = null;
					for (int j = 0; true; j++) {
						obj = (JsonObject) jsonObject.get(Integer.toString(j));
						if (obj == null) break;
						busRoute = new BusRouteResponseDTO(null,
							Double.valueOf(obj.get("lat").toString().replace("\"", "")),
							Double.valueOf(obj.get("lng").toString().replace("\"", ""))
						);
						busRoutes.add(busRoute);
						//System.out.println(obj.get("lat").toString().replace("\"", "") + " - " + obj.get("lng").toString().replace("\"", ""));
					}
					busLine.setBusRoutes(busRoutes);
					busLines.add(busLine);
					this.busLineService.saveBusLine(busLine);
				}

				//Aguarda 1 segundo
				Thread.sleep(1000);
			}
			System.out.println("BASE DE DADOS CARREGADA");
		}
	}

	private static void saveBusLineToDisk() throws Exception {

		HttpClient httpClient = HttpClientBuilder.create().build();
		Gson g = new Gson();
		List<BusLine> busLines = new ArrayList<>();
		List<BusRoute> busRoutes = null;
		String url = null;
		HttpGet request = null;
		HttpResponse response = null;
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject, obj;
		JsonArray jsonArray;
		FileWriter file = null;

		url = "http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=&t=o";
		request = new HttpGet(url);
		response = httpClient.execute(request);
		if (response != null) {
			InputStream source = response.getEntity().getContent(); //Get the data in the entity
			Reader reader = new InputStreamReader(source);
			jsonArray = (JsonArray) jsonParser.parse(reader);
			reader.close();
			source.close();
			file = new FileWriter("/tmp/onibus/busLines.json");
			file.write(jsonArray.toString());
			file.close();
			System.out.println("SALVANDO DADOS");
			for (JsonElement lin: jsonArray) {
				jsonObject = lin.getAsJsonObject();
				BusLine busLine = new BusLine(
					Long.parseLong(jsonObject.get("id").toString().replace("\"", "")),
					jsonObject.get("codigo").toString().replace("\"", ""),
					jsonObject.get("nome").toString().replace("\"", ""));

				url = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=" + busLine.getIdBusLine();
				request = new HttpGet(url);
				response = httpClient.execute(request);
				if (response != null) {
					source = response.getEntity().getContent(); //Get the data in the entity
					reader = new InputStreamReader(source);
					jsonObject = (JsonObject) jsonParser.parse(reader);
					reader.close();
					source.close();

					file = new FileWriter("/tmp/onibus/busLine-" + busLine.getIdBusLine() + ".json");
					file.write(jsonObject.toString());
					file.close();

					busRoutes = new ArrayList<>();
					BusRoute busRoute = null;
					for (int j = 0; true; j++) {
						obj = (JsonObject) jsonObject.get(Integer.toString(j));
						if (obj == null) break;
						busRoute = new BusRoute(busLine,
							Double.valueOf(obj.get("lat").toString().replace("\"", "")),
							Double.valueOf(obj.get("lng").toString().replace("\"", ""))
						);
						busRoutes.add(busRoute);
						//System.out.println(obj.get("lat").toString().replace("\"", "") + " - " + obj.get("lng").toString().replace("\"", ""));
					}
					busLine.setBusRoutes(busRoutes);
					busLines.add(busLine);
				}
				//Aguarda 1 segundo
				Thread.sleep(1000);
			}
			System.out.println("DADOS SALVOS");
		}
	}
	private void loadBusLineFromFile () throws Exception {
		Gson g = new Gson();
		List<BusLineDTO> busLines = new ArrayList<>();
		List<BusRouteResponseDTO> busRoutes = null;
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject, obj;
		JsonArray jsonArray;

		InputStream source = new FileInputStream("/tmp/onibus/busLines.json");

		if (source != null) {
			Reader reader = new InputStreamReader(source);
			jsonArray = (JsonArray) jsonParser.parse(reader);
			reader.close();
			source.close();
			System.out.println("CARREGANDO DADOS ...");
			for (JsonElement lin : jsonArray) {
				jsonObject = lin.getAsJsonObject();
				BusLineDTO busLine = new BusLineDTO(
					Long.parseLong(jsonObject.get("id").toString().replace("\"", "")),
					jsonObject.get("codigo").toString().replace("\"", ""),
					jsonObject.get("nome").toString().replace("\"", ""));

				//System.out.println(busLine.getIdBusLine() + " - " + busLine.getCode()+ " - " + busLine.getName());
				source = new FileInputStream("/tmp/onibus/busLine-" + busLine.getId() + ".json");

				reader = new InputStreamReader(source);
				jsonObject = (JsonObject) jsonParser.parse(reader);
				reader.close();
				source.close();

				busRoutes = new ArrayList<>();
				BusRouteResponseDTO busRoute = null;
				for (int j = 0; true; j++) {
					obj = (JsonObject) jsonObject.get(Integer.toString(j));
					if (obj == null) break;
					busRoute = new BusRouteResponseDTO(null,
						Double.valueOf(obj.get("lat").toString().replace("\"", "")),
						Double.valueOf(obj.get("lng").toString().replace("\"", ""))
					);
					busRoutes.add(busRoute);
					busLine.setBusRoutes(busRoutes);

					//System.out.println(obj.get("lat").toString().replace("\"", "") + " - " + obj.get("lng").toString().replace("\"", ""));
				}
				busLines.add(busLine);
				this.busLineService.saveBusLine(busLine);
			}
			System.out.println("DADOS CARREGADOS");
		}
	}
}
