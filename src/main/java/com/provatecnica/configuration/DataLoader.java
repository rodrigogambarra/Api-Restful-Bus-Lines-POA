package com.provatecnica.configuration;

import com.google.gson.*;
import com.provatecnica.model.BusLine;
import com.provatecnica.model.BusRoute;
import com.provatecnica.repository.BusLineRepository;
import com.provatecnica.repository.BusRouteRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

	private BusLineRepository busLineRepository;
	private BusRouteRepository busRouteRepository;

	@Autowired
	public DataLoader(BusLineRepository busLineRepository, BusRouteRepository busRouteRepository) {
		this.busLineRepository = busLineRepository;
		this.busRouteRepository = busRouteRepository;
	}

	public void run(ApplicationArguments args) throws Exception {

		File file = new File("/tmp/onibus/");
		if (!file.exists()) {
			file.mkdir();
			saveBusLineToDisk();
			loadBusLineFromFile();
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
					for (BusRoute itiner: busLine.getBusRoutes()) {
						System.out.println(busLine.getIdBusLine() + " - " + busLine.getCode() + " - " + busLine.getName() + " - " + itiner.getLatitude() + " - " + itiner.getLongitude());
					}
					busLines.add(busLine);
				}
				//Aguarda 1 segundo
				Thread.sleep(1000);
			}
		}
	}

	private void loadBusLineFromFile() throws Exception {

		Gson g = new Gson();
		List<BusLine> busLines = new ArrayList<>();
		List<BusRoute> busRoutes = null;
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject, obj;
		JsonArray jsonArray;

		InputStream source = new FileInputStream("/tmp/onibus/busLines.json");

		if (source != null) {
			Reader reader = new InputStreamReader(source);
			jsonArray = (JsonArray) jsonParser.parse(reader);
			reader.close();
			source.close();

			for (JsonElement lin: jsonArray) {
				jsonObject = lin.getAsJsonObject();
				BusLine busLine = new BusLine(
					Long.parseLong(jsonObject.get("id").toString().replace("\"", "")),
					jsonObject.get("codigo").toString().replace("\"", ""),
					jsonObject.get("nome").toString().replace("\"", ""));

				this.busLineRepository.saveAndFlush(busLine);

				//System.out.println(busLine.getId() + " - " + busLine.getCodigo() + " - " + busLine.getNome());
				source = new FileInputStream("/tmp/onibus/busLine-" + busLine.getIdBusLine() + ".json");

				if (source != null) {
					reader = new InputStreamReader(source);
					jsonObject = (JsonObject) jsonParser.parse(reader);
					reader.close();
					source.close();

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
						busLine.setBusRoutes(busRoutes);

						this.busRouteRepository.saveAndFlush(busRoute);
						System.out.println(obj.get("lat").toString().replace("\"", "") + " - " + obj.get("lng").toString().replace("\"", ""));
					}
					busLines.add(busLine);
				}
			}
		}
	}
}
