package motogpAPI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.web.LeagueController;
import org.springframework.security.acls.model.NotFoundException;

import motogpAPI.model2.Detail;
import motogpAPI.model2.Example2;
import motogpAPI.model.Example;
import motogpAPI.model.InfoCarrera;

@Slf4j
public class PeticionesGet {
	private static final String URL = "https://results.motorsportstats.com/";
	private static final String URL_JSON_SESSIONS = "https://mssproxy.motorsportstats.com/web/3.0.0/sessions/";
	private static final String URL_JSON_SEASONS = "https://mssproxy.motorsportstats.com/web/3.0.0/seasons/";
	
	private static RaceCode raceCode= null;
	private static int raceNumber = -1;
	
	// Poner con JPA que anyo debe tener un @range de 2005 a 2020
	public static Record obtieneRecords(Integer anyo, Pais pais, Category categoria) throws IOException {
		Record res = new Record();
		if (!pais.equals(Pais.NOTFOUND) && anyo > 2004 && anyo < 2021) {
			String urlBuilder = "https://www.motogp.com/es/ajax/results/parse/" + anyo + "/" + pais + "/" + categoria + "/";
			res = new Record(urlBuilder);
		}
		return res;
	}
	
//	public static Record parseRecord(String entrada) throws IOException {
//		Record res = new Record();
//		String html = Jsoup.connect(entrada).get().text();
//		if (!html.contains("Récords: ") ) {
//			res.vueltaDePole = null;
//			res.vueltaRapida = null;
//			res.recordDelCircuito = null;
//			res.mejorVuelta = null;
//		} else {
//			String records = html.split("Récords: ")[1];
//			String[] prueba = records.split("Km/h");
//			List<String> listaRecords = new ArrayList<String>();
//			for (int i=0; i < prueba.length; i++) {
//				listaRecords.add(prueba[i].trim());
//			}
//
//			res.vueltaDePole = parseRecordPole(prueba[0]);
//			res.vueltaRapida = parseRecordVueltaRapida(prueba[1]);
//			res.recordDelCircuito = parseRecordCircuito(prueba[2]);
//			res.mejorVuelta = parseRecordMejorVuelta(prueba[3]);
//		}
//		return res;
//	}
	
//	public static RecordMejorVuelta parseRecordMejorVuelta(String entrada) {
//		RecordMejorVuelta res = new RecordMejorVuelta();
//		
//		String atributos = entrada.split(":")[1].trim();
//		String[] split = atributos.split(" ");
//		Integer x = split.length;
//		if (x < 4) {
//			res.nombrePiloto = null;
//			res.tiempo = null;
//			res.kmh = null;
//			res.anyo= null;
//		} else { 
//			res.nombrePiloto = split[1];
//			for (int i=2; i < x-2; i++) {
//				res.nombrePiloto += " " + split[i];
//			}
//			String[] tiempoSplit = split[x-2].split("'");
//			res.tiempo = (int) ((Integer.parseInt(tiempoSplit[0]) * 60 + Double.parseDouble(tiempoSplit[1]) + 1e-14) * 1000);
//			res.kmh = Double.parseDouble(split[x-1]);
//			res.anyo = Integer.parseInt(split[0]);
//		}
//		return res;
//	}

//	public static RecordVueltaRapida parseRecordVueltaRapida(String entrada) {
//		RecordVueltaRapida res = new RecordVueltaRapida();
//		
//		String atributos = entrada.split(":")[2].trim();
//		String[] split = atributos.split(" ");
//		Integer x = split.length;
//		if (x < 4) {
//			res.nombrePiloto = null;
//			res.tiempo = null;
//			res.kmh = null;
//			res.vuelta = null;
//		} else {
//			res.nombrePiloto = split[1];
//			for (int i=2; i < x-2; i++) {
//				res.nombrePiloto += " " + split[i];
//			}
//			String[] tiempoSplit = split[x-2].split("'");
//			res.tiempo = (int) ((Integer.parseInt(tiempoSplit[0]) * 60 + Double.parseDouble(tiempoSplit[1]) + 1e-14) * 1000);
//			res.kmh = Double.parseDouble(split[x-1]);
//			res.vuelta = Integer.parseInt(split[0]);
//		}
//		return res;
//	}

//	public static RecordPole parseRecordPole(String entrada) {
//		RecordPole res = new RecordPole();
//		String atributos = entrada.split(":")[1].trim();
//		String[] split = atributos.split(" ");
//		Integer x = split.length;
//		if (x < 4) {
//			res.nombrePiloto = null;
//			res.tiempo = null;
//			res.kmh = null;
//		} else {
//			res.nombrePiloto = split[0];
//			for (int i=1; i < x-2; i++) {
//				res.nombrePiloto += " " + split[i];
//			}
//			String[] tiempoSplit = split[x-2].split("'");
//			res.tiempo = (int) ((Integer.parseInt(tiempoSplit[0]) * 60 + Double.parseDouble(tiempoSplit[1]) + 1e-14) * 1000);
//			res.kmh = Double.parseDouble(split[x-1]);
//		}
//		return res;
//	}

//	public static RecordCircuito parseRecordCircuito(String entrada) throws IOException {
//		RecordCircuito res = new RecordCircuito();
//		
//		String atributos = entrada.split(":")[1].trim();
//		String[] split = atributos.split(" ");
//		Integer x = split.length;
//		if (x < 4) {
//			res.setNombrePiloto(null);
//			res.setTiempo(null);
//			res.setKmh(null);
//			res.setAnyo(null);
//		} else {
//			res.setNombrePiloto(split[1]);
//			for (int i=2; i < x-2; i++) {
//				res.setNombrePiloto(res.getNombrePiloto() + " " + split[i]);
//			}
//			String[] tiempoSplit = split[x-2].split("'");
//			res.setTiempo((int) ((Integer.parseInt(tiempoSplit[0]) * 60 + Double.parseDouble(tiempoSplit[1]) + 1e-14) * 1000));
//			res.setKmh(Double.parseDouble(split[x-1]));
//
//			res.setAnyo(Integer.parseInt(split[0]));
//		}
//		
//		return res;
//	}
	
	public static List<InfoCarrera> getResultsByRaceNumberCampu(Category category, int year, int raceNumber,Session session) throws JSONException, IOException {
		JSONObject result = new JSONObject();
		JSONObject result2= new JSONObject();

		List<InfoCarrera> listaCarrera = new ArrayList<InfoCarrera>();

		try {
			String url;
			if (category == Category.MOTOGP)
				url = URL_JSON_SEASONS + year + "-" + category.toString().toLowerCase() + "/standings/drivers";
			else
				url = URL_JSON_SEASONS + category.toString().toLowerCase() + "-" + year + "/standings/drivers";

			String refer = URL + "series/" + category.toString().toLowerCase();
			result = new JSONObject(JsonReader.readJsonFromUrl(url, refer, URL));//.getJSONArray("standings");
			
			System.out.println("Result" +result);
			
			System.out.println("url: " + url);
			System.out.println("refer: " + refer);
			System.out.println("URL (origin): " + URL);
			
			Example example = new ObjectMapper().readValue(result.toString(),Example.class);
			
			List<String> idCarreras = new ArrayList<String>();
			for(int i=0;i<example.getRaces().size();i++) {
				idCarreras.add(example.getRaces().get(i).getSession().getUuid());
				
			}
			String urlId = "https://mssproxy.motorsportstats.com/web/3.0.0/sessions/"+idCarreras.get(raceNumber) +"/classification";
			
			result2 = new JSONObject(JsonReader.readJsonFromUrl(urlId, refer, URL));
			
			System.out.println("Result2"+result2);
			
			Example2 example2 = new ObjectMapper().readValue(result2.toString(),Example2.class);
			
			String nombreEvento = example2.getEvent().getName();
//			List<InfoCarrera> listaCarrera = new ArrayList<InfoCarrera>();
			String raceCode = example2.getEvent().getCode();
			String urlPeticionFecha = "https://www.motogp.com/es/ajax/results/parse/"+ year + "/"+raceCode+ "/"+category.toString()+"/";
			String html = Jsoup.connect(urlPeticionFecha).get().text();

			String fecha_lugar = html.split("Pos.")[0].split("Clasificación de Carrera")[1].substring(5);
// example Phillip Island, Sunday, October 23, 2016 
			String fecha_exacta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(example2.getDate() * 1000L)).split(" ")[0];
			String lugar= fecha_lugar.split(", ")[0];
			for(int i=0;i<example2.getDetails().size();i++) {
//				System.out.println(example2.getEvent().getCode());
//				System.out.println(example2.getDate());

				Integer posicion = example2.getDetails().get(i).getFinishPosition();
				Integer numero = Integer.parseInt(example2.getDetails().get(i).getCarNumber());
				String piloto = example2.getDetails().get(i).getDrivers().get(0).getName();
				String pais ="";
				try {
					 pais += example2.getDetails().get(i).getNationality().getName();

				}catch (Exception e) {
					
				}
				
				String equipo = example2.getDetails().get(i).getTeam().getName();
				Double kmh = example2.getDetails().get(i).getAvgLapSpeed();
				Integer vueltaMasRapidaPole =example2.getDetails().get(i).getBestLap().getTime();
				InfoCarrera n = new InfoCarrera(nombreEvento, posicion,calculaPuntos(posicion), numero, 
						piloto, pais, equipo, kmh, vueltaMasRapidaPole,raceCode,category,lugar,fecha_exacta);
				listaCarrera.add(n);
				}

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		if(listaCarrera.isEmpty()) {
			throw new NotFoundException("No se han encontrado la carrera '"+raceNumber+"' para los anyos dados");
		}
		return listaCarrera;
	}
	
	public List<RiderOnlineData> getResultsByRaceNumber(Category category, int year, int raceNumber, Session session) {
		List<RiderOnlineData> result = new ArrayList<>();

		try {
			// Requests the JSON object from the website
			this.raceNumber = raceNumber;
			JSONObject jsonObject = getJsonObjectResults(category, year, session);

			// Converts the JSON object into a list of RiderOnlineData
			JSONArray gridJSON = jsonObject.getJSONArray("details");
			result = getRiderList(gridJSON);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			// Reset of the values before returning the results
			raceCode = null;
			this.raceNumber = -1;
			return result;
		}
	}
	
	public List<RiderOnlineData> getResultsByRaceCode(Category category, int year, RaceCode code, Session session) {
		List<RiderOnlineData> result = new ArrayList<>();

			// Requests the JSON object from the website
			raceCode = code;
			JSONObject jsonObject = getJsonObjectResults(category, year, session);

			// Converts the JSON object into a list of RiderOnlineData
			JSONArray gridJSON = jsonObject.getJSONArray("details");
			result = getRiderList(gridJSON);
			// Reset of the values before returning the results
			raceCode = null;
			raceNumber = -1;
			return result;
	}
	
	public static List<InfoCarrera> getResultsByRaceCodeCampu(Category category, int year, RaceCode code, Session session) throws IOException {
		

			// Requests the JSON object from the website
			raceCode = code;
			JSONObject jsonObject = getJsonObjectResults(category, year, session);

			System.out.println(jsonObject);
			raceCode = null;
			raceNumber = -1;
			Example2 example2 = new ObjectMapper().readValue(jsonObject.toString(),Example2.class);

			List<InfoCarrera> listaCarrera = new ArrayList<InfoCarrera>();
			if(example2.getEvent()==null) {
				log.warn("La api no podido obtener los resultados satisfactoriamente para year :" + year+", category:" + category);
				throw new NotFoundException("No se han encontrado carreras para los parametros dados");
			}
			String nombreEvento = example2.getEvent().getName();
			String raceCode = example2.getEvent().getCode();
			System.out.println(nombreEvento);

			String fecha_exacta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(example2.getDate() * 1000L)).split(" ")[0];
			for(int i=0;i<example2.getDetails().size();i++) {

				Integer posicion = example2.getDetails().get(i).getFinishPosition();
				Integer numero = Integer.parseInt(example2.getDetails().get(i).getCarNumber());
				String piloto = example2.getDetails().get(i).getDrivers().get(0).getName();
				String pais ="";
				try {
					 pais += example2.getDetails().get(i).getNationality().getName();

				}catch (Exception e) {
					
				}
				
				String equipo = example2.getDetails().get(i).getTeam().getName();
				Double kmh = example2.getDetails().get(i).getAvgLapSpeed();
				Integer vueltaMasRapidaPole =example2.getDetails().get(i).getBestLap().getTime();
				InfoCarrera n = new InfoCarrera(nombreEvento, posicion,calculaPuntos(posicion), numero, 
						piloto, pais, equipo, kmh, vueltaMasRapidaPole,raceCode,category,nombreEvento,fecha_exacta);
				listaCarrera.add(n);
				}

			return listaCarrera;
	}
	
	private static JSONObject getJsonObjectResults(Category category, int year, Session session) {
		JSONObject result = new JSONObject();

		try {
			String baseURL = getCompleteURL(category, year);
			Element mainResult = null;

			String sessionName = "";
			switch (session){
				case FP1: sessionName = "1st Practice"; break;
				case FP2: sessionName = "2nd Practice"; break;
				case FP3: sessionName = "3rd Practice"; break;
				case FP4: sessionName = "4th Practice"; break;
				case QP1: sessionName = "1st Qualifying"; break;
				case QP2: sessionName = "2nd Qualifying"; break;
				case QP: sessionName = "Qualifying"; break;
				case WARMUP: sessionName = "Warm Up"; break;
				case GRID: sessionName = "Grid"; break;
				case RACE: sessionName = "Race";
			}

			// Gets the button of the selected session based on its text
			int found = 0;
			for (Element temp : Jsoup.connect(baseURL).get().getElementsByClass("_1CDKX").get(1).children()) {
				if (temp.text().equals(sessionName)) {
					mainResult = temp;
					found++;
					break;
				}
			}

			if (found == 0){
				System.out.println("\nThe requested session or data (" + sessionName + ") is not available for the " + year + " season...");
			}

			// Gets the urlCode to request the JSONObject from the website
			String[] temp = mainResult.attr("href").split("/");

			String urlCode = temp[temp.length - 1];
			System.out.println(urlCode);
			String url = URL_JSON_SESSIONS + urlCode + "/classification";
			result = new JSONObject(JsonReader.readJsonFromUrl(url, baseURL, URL_JSON_SESSIONS));

		} catch (IOException e) {
		}

		return result;
	}
	
	private List<RiderOnlineData> getRiderList(JSONArray classification){
		List<RiderOnlineData> result = new ArrayList<>();

		for (int i = 0; i<classification.length(); i++){
			JSONObject rider = classification.getJSONObject(i);

			int position = rider.getInt("finishPosition"); // 0 if not classified
			String name = rider.getJSONArray("drivers").getJSONObject(0).getString("name");
			int time = rider.getInt("time"); // 0 if not available
			int laps = rider.getInt("laps"); // 0 if not available
			String team = rider.getJSONObject("team").getString("name");

			String nationality;
			try{
				nationality = rider.getJSONObject("nationality").getString("name");
			}catch (Exception e){
				nationality = "Not Available";
			}

			int number; // In case the number is not available, this value will be -1
			try{
				number = rider.getInt("carNumber");
			} catch (Exception e){
				//e.printStackTrace();
				number = -1;
			}
//			result2.add(new InfoCarrera(nombreEvento, position, calculaPuntos(position), number, name, nationality, team, kmh, vueltaMasRapidaPole, raceCode, category, lugar, fecha));
			result.add(new RiderOnlineData(position, number, name, nationality, team, time, laps));
		}
		return result;
	}
	
	private static String getCompleteURL(Category category, int year) throws IOException {
		String result = URL;
		String grandprix = "";

		String urlRequest;
		if (category == Category.MOTOGP)
			urlRequest = URL_JSON_SEASONS + year + "-" + category.toString().toLowerCase() + "/races";
		else
			 urlRequest = URL_JSON_SEASONS + category.toString().toLowerCase() + "-" + year + "/races";

		// Requests the JSONArray containing the races' details
		String referer = URL + "series/" + category.toString().toLowerCase() + "/season/" + year;
		String origin = URL;
		JSONArray races = new JSONArray(JsonReader.readJsonFromUrl(urlRequest, referer, origin));


		if (raceCode != null) {
			int flag = 0;
			for (int i = 0; i<races.length(); i++) {
				JSONObject event = races.getJSONObject(i).getJSONObject("event");
				if (event.getString("code").equals(raceCode.toString())) {
					grandprix = event.getString("uuid");
					break;
				}
				// The Motorsport Stats' website uses the same race code for two different races in the 2002 and 2003 seasons
				else if (event.getString("code").equals("JPN") && raceCode.equals(RaceCode.PAC) && (year == 2003 || year == 2002)) {
					if (flag == 0) {
						flag++;
						continue;
					} else {
						grandprix = event.getString("uuid");
						break;
					}
				}
			}
		} else if (raceNumber > 0) {
			for (int i = 0; i<races.length(); i++) {
				JSONObject event = races.getJSONObject(i).getJSONObject("event");
				if (raceNumber == i + 1) {
					System.out.println("Selected raceCode: " + event.getString("code"));
					grandprix = event.getString("uuid");
					break;
				}
			}
		}

		result += "results/" + grandprix + "/classification/";
		System.out.println(result);
		return result;
	}
	
	
	public Pais parseRaceCodeToPais(RaceCode raceCode) {
		Pais pais = Pais.NOTFOUND;

		switch (raceCode) {
		case ESP:
			pais = Pais.SPA;
			break;
		case DEU:
			pais = Pais.GER;
			break;
		case IND:
			pais = Pais.INP;
			break;
		case PRT:
			pais = Pais.POR;
			break;
		case SMR:
			pais = Pais.RSM;
			break;
		default:
			try {
				pais = Pais.valueOf(raceCode.toString());
			} catch (Exception e) {
				System.out.println(raceCode.toString() + " code not found in the list of country codes!");
			}
			break;
		}

		return pais;
	}
	
	public static Integer calculaPuntos(Integer pos) {
		switch (pos) {
		case 1:	return 25;
		case 2:	return 20;
		case 3:	return 16;
		case 4:	return 13;
		case 5:	return 11;
		case 6:	return 10;
		case 7:	return 9;
		case 8:	return 8;
		case 9:	return 7;
		case 10:return 6;
		case 11:return 5;
		case 12:return 4;
		case 13:return 3;
		case 14:return 2;
		case 15:return 1;
		default:return 0;
		}
	}
	
}