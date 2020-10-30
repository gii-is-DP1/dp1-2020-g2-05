package motogpAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import motogpAPI.model.Example;
import motogpAPI.model.InfoCarrera;
import motogpAPI.model2.Example2;

public class testing2 {
	private static final String URL = "https://results.motorsportstats.com/";
//	private static final String URL_JSON_SESSIONS = "https://mssproxy.motorsportstats.com/web/3.0.0/sessions/";
	private static final String URL_JSON_SEASONS = "https://mssproxy.motorsportstats.com/web/3.0.0/seasons/";


	public static void main(String[] args) throws Exception {
		
		
		
			Category category = Category.Moto2;
			int year =2019;
			
			List<InfoCarrera> solucion = getResultsByRaceNumber(category, year, 0,Session.FP1);
			
			System.out.println(solucion);
	
	}
	public static List<InfoCarrera> getResultsByRaceNumber(Category category, int year, int raceNumber,Session session) throws JSONException, IOException {
		JSONObject result = new JSONObject();
		JSONObject result2= new JSONObject();

		String url;
		if (category == Category.MotoGP)
			url = URL_JSON_SEASONS + year + "-" + category.toString().toLowerCase() + "/standings/drivers";
		else
			url = URL_JSON_SEASONS + category.toString().toLowerCase() + "-" + year + "/standings/drivers";

		String refer = URL + "series/" + category.toString().toLowerCase();
		result = new JSONObject(JsonReader.readJsonFromUrl(url, refer, URL));//.getJSONArray("standings");

		Example example = new ObjectMapper().readValue(result.toString(),Example.class);
		

		List<String> idCarreras = new ArrayList<String>();
		for(int i=0;i<example.getRaces().size();i++) {
			idCarreras.add(example.getRaces().get(i).getSession().getUuid());
			
		}
		String urlId = "https://mssproxy.motorsportstats.com/web/3.0.0/sessions/"+idCarreras.get(raceNumber) +"/classification";
		
		result2 = new JSONObject(JsonReader.readJsonFromUrl(urlId, refer, URL));
		
		Example2 example2 = new ObjectMapper().readValue(result2.toString(),Example2.class);
		
		String nombreEvento = example2.getEvent().getName();
		List<InfoCarrera> listaCarrera = new ArrayList<InfoCarrera>();
		for(int i=0;i<example2.getDetails().size();i++) {
			
			Integer posicion = example2.getDetails().get(i).getFinishPosition();
			Integer numero = Integer.parseInt(example2.getDetails().get(i).getCarNumber());
			String piloto = example2.getDetails().get(i).getDrivers().get(0).getName();
			String pais = example2.getDetails().get(i).getNationality().getName();
			String equipo = example2.getDetails().get(i).getTeam().getName();
			Double kmh = example2.getDetails().get(i).getAvgLapSpeed();
			Boolean vueltaMasRapidaPole =example2.getDetails().get(i).getBestLap().getFastest();
			InfoCarrera n = new InfoCarrera(nombreEvento, posicion,calculaPuntos(posicion), numero, piloto, pais, equipo, kmh, vueltaMasRapidaPole);
			listaCarrera.add(n);
			}
		return listaCarrera;
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