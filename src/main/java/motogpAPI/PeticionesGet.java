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

import motogpAPI.model2.Detail;
import motogpAPI.model2.Example2;
import motogpAPI.model.Example;
import motogpAPI.model.InfoCarrera;

public class PeticionesGet {
	private static final String URL = "https://results.motorsportstats.com/";
	private static final String URL_JSON_SESSIONS = "https://mssproxy.motorsportstats.com/web/3.0.0/sessions/";
	private static final String URL_JSON_SEASONS = "https://mssproxy.motorsportstats.com/web/3.0.0/seasons/";
	
	private static RaceCode raceCode= null;
	private static int raceNumber = -1;
	
	// Poner con JPA que anyo debe tener un @range de 2002 a 2020
	public static Record obtieneRecords(String anyo, Pais pais, Category categoria) throws IOException {
		String urlBuilder = "https://www.motogp.com/es/ajax/results/parse/" + anyo + "/" + pais + "/" + categoria + "/";
		return new Record(urlBuilder);
	}
	
	public static List<InfoCarrera> getResultsByRaceNumberCampu(Category category, int year, int raceNumber,Session session) throws JSONException, IOException {
		JSONObject result = new JSONObject();
		JSONObject result2= new JSONObject();

		List<InfoCarrera> listaCarrera = new ArrayList<InfoCarrera>();

		try {
			String url;
			if (category == Category.MotoGP)
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

	
			raceCode = null;
			raceNumber = -1;
			Example2 example2 = new ObjectMapper().readValue(jsonObject.toString(),Example2.class);

			List<InfoCarrera> listaCarrera = new ArrayList<InfoCarrera>();

			String nombreEvento = example2.getEvent().getName();
			String raceCode = example2.getEvent().getCode();
			String urlPeticionFecha = "https://www.motogp.com/es/ajax/results/parse/"+ year + "/"+raceCode+ "/"+category.toString()+"/";
			String html = Jsoup.connect(urlPeticionFecha).get().text();

			String fecha_lugar = html.split("Pos.")[0].split("Clasificación de Carrera")[1].substring(5);
// example Phillip Island, Sunday, October 23, 2016 
			String fecha_exacta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(example2.getDate() * 1000L)).split(" ")[0];
			String lugar= fecha_lugar.split(", ")[0];
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
						piloto, pais, equipo, kmh, vueltaMasRapidaPole,raceCode,category,lugar,fecha_exacta);
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
			e.printStackTrace();
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
		if (category == Category.MotoGP)
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