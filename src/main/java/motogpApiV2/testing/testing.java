package motogpApiV2.testing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.service.PilotService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import motogpAPI.RaceCode;
import motogpApiV2.GranPremioDetails.GranPremioDetails;
import motogpApiV2.races.Races;
import motogpApiV2.races.Schedule;
import motogpApiV2.results.Competitor;
import motogpApiV2.results.Results;
import motogpApiV2.stage.Stage;
import motogpApiV2.stage.Season;
@Slf4j

public class testing {
	
	private static String API_KEY;
	private static final String API_KEY_GP = "4g2egaffth2xth2b49mt785e";
	private static final String API_KEY_2 = "w7hkcb4ptcumtv2scrcd9hqh";
	private static final String API_KEY_3 = "rz5ru3a4r49mwawhv634556x";
	
	private static final String rootUrl = "https://api.sportradar.us/motogp/trial/v2/en/sport_events/";
	
	private static final String urlToObtainSeasons = "https://api.sportradar.us/motogp/trial/v2/en/seasons.json?api_key=";
	private static final String urlToObtainResults = "/summary.json?api_key=";
	private static final String urlToObtainSchedules = "/schedule.json?api_key=";

	public static void main(String[] args) throws IOException, InterruptedException {

	}

	public static String getJsonFromUrl(String url) throws IOException {

		URL uri = new URL(url);
		URLConnection yc = uri.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();

		return response.toString();
	}

	public static String getSeasonByCategoryAndYear(Integer yearToRequest, String categoryToRequest)
			throws JsonMappingException, JsonProcessingException, IOException {
		String idToFind = "";

		String urlToGetRequestApi = urlToObtainSeasons + API_KEY;
		Stage allSeasons = new ObjectMapper().readValue(getJsonFromUrl(urlToGetRequestApi), Stage.class);

		for (int i = 0; i < allSeasons.getNumOfSeasons(); i++) {

			Season season_i = allSeasons.getStages().get(i);

			if (season_i.getDescription().split(" ")[0].equals(categoryToRequest.toString())
					&& season_i.getDescription().split(" ")[1].equals(yearToRequest.toString())) {
				idToFind += season_i.getId();
				break;
			}
		}

		return idToFind;
	}

	public static String getScheduleByRaceCodeAndYear(Integer yearToRequest, RaceCode raceCodeToRequest,
			String seasonIdToRequestItsSchedule)
			throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		TimeUnit.SECONDS.sleep(1);

		String raceCodeParseado = raceCodeToStringParser(raceCodeToRequest);
		String idToFind = "";

		String urlToGetRequestApi = rootUrl + seasonIdToRequestItsSchedule + urlToObtainSchedules + API_KEY;
		Schedule schedules = new ObjectMapper().readValue(getJsonFromUrl(urlToGetRequestApi), Schedule.class);

		Integer numeroDeGrandesPremios = schedules.getListOfRacesScheduled().size();
		for (int i = 0; i < numeroDeGrandesPremios; i++) {

			Races stage_i = schedules.getListOfRacesScheduled().get(i);

			if (stage_i.getDescription().equals(raceCodeParseado + " " + yearToRequest.toString())) {
				
				Integer numeroDePracticas = stage_i.getNumeroPracticas();
				
				for (int j = 0; j < numeroDePracticas; j++) {
					
					if (stage_i.getListaPracticas().get(j).getDescription().equals("Race")) {
						
						idToFind += stage_i.getListaPracticas().get(j).getId();
					}
				}
				break;
			}
		}

		return idToFind;
	}

	public static List<Competitor> getResultsByScheduleId(String scheduleId)
			throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		
		String idToFind = "";
		
		String urlToGetRequestApi = rootUrl + scheduleId + urlToObtainResults + API_KEY;
		
		Results results = new ObjectMapper().readValue(getJsonFromUrl(urlToGetRequestApi), Results.class);

		return results.getStage().getCompetitors();
	}

	public static List<Competitor> findCompetitorAndItsResultsByCategoryRaceCodeAndYear(Integer yearToRequest,
			RaceCode raceCodeToRequest, Category categoryToRequest)
			throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {

		if(categoryToRequest.equals(Category.MOTOGP)) {
			API_KEY=API_KEY_GP;
		}else if(categoryToRequest.equals(Category.MOTO2)) {
			API_KEY=API_KEY_2;
		}else if(categoryToRequest.equals(Category.MOTO3)){
			API_KEY=API_KEY_3;
		}
		
		
		String idOfScheduleWanted = getSeasonByCategoryAndYear(yearToRequest, categoryParserToApi(categoryToRequest));

		String idOfResultsWanted = getScheduleByRaceCodeAndYear(yearToRequest, raceCodeToRequest, idOfScheduleWanted);

		List<Competitor> competitorsFound = getResultsByScheduleId(idOfResultsWanted);
		if(competitorsFound.size()==0 || competitorsFound==null) {
			throw new FileNotFoundException();
		}
		return competitorsFound;
	}

	public static List<GranPremio> obtainScheduleForAGivenYearAndGivenCategory(Integer yearToRequest,Category categoryToRequest) throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		API_KEY=API_KEY_3;

		String idToObtainSchedules =getSeasonByCategoryAndYear(yearToRequest, categoryParserToApi(categoryToRequest));

		System.out.println("idToObtainSchedules : " + idToObtainSchedules);
		String urlToGetRequestApi = rootUrl+idToObtainSchedules+urlToObtainSchedules + API_KEY;
		Stage allSeasons = new ObjectMapper().readValue(getJsonFromUrl(urlToGetRequestApi), Stage.class);	
		List<GranPremio> granPremiosToReturn = new ArrayList<GranPremio>();
		for(int i=0;i<allSeasons.getNumOfSeasons();i++) {
			Season stage = allSeasons.getStages().get(i);
			String idToObtainGPDetails = stage.getId();
			String uri =rootUrl+idToObtainGPDetails+urlToObtainResults+API_KEY;
			TimeUnit.SECONDS.sleep(1);
			GranPremio gp = new GranPremio();
			GranPremioDetails detailsOfGP_i = new ObjectMapper().readValue(getJsonFromUrl(uri), GranPremioDetails.class);	
			gp.setCalendar(true);
			gp.setCircuit(detailsOfGP_i.getStage().getVenue().getName());
			
			//parsing date sample : 2020-03-06T10:40:00+00:00 to localdate
			String[] dateToParse = detailsOfGP_i.getStage().getScheduled().split("T")[0].split("-");
			gp.setDate0(LocalDate.of(Integer.parseInt(dateToParse[0])
					,Integer.parseInt(dateToParse[1])
					,Integer.parseInt(dateToParse[2])));
			gp.setSite(detailsOfGP_i.getStage().getVenue().getCity());
			gp.setHasBeenRun(false);
			gp.setRaceCode(detailsOfGP_i.getStage().getVenue().getCountryCode());

			granPremiosToReturn.add(gp);
			
			log.info("Gp creado correctamente :"+gp);
		}
		
		return granPremiosToReturn;
		
	}		
	
	public static String raceCodeToStringParser(RaceCode raceCode) {
		switch (raceCode) {
		case QAT:
			return "Grand Prix of Qatar";
		case ARG:
			return "Gran Premio de la Republica Argentina";
		case AME:
			return "Grand Prix of The Americas";
		case ESP:
			return "Gran Premio de Espana";
		case FRA:
			return "Grand Prix de France";
		case ITA:
			return "Gran Premio d'Italia";
		case CAT:
			return "Circuit de Barcelona-Catalunya";
		case NLD:
			return "TT Assen";
		case DEU:
			return "Motorrad Grand Prix Deutschland";
		case CZE:
			return "Grand Prix Ceske Republiky";
		case AUT:
			return "Motorrad Grand Prix von Österreich";
		case ENG:
			return "British Grand Prix";
		case SMR:
			return "Gran Premio di San Marino e della Riviera di Rimini";
		case ARA:
			return "Gran Premio de Aragon";
		case USA:
			return "Grand Prix of The Americas";
		case THA:
			return "Thailand Grand Prix";
		case JPN:
			return "Grand Prix of Japan";		
		case AUS:
			return "Australian Grand Prix";
		default:
			return "";
		}
	}
	
	public static String categoryParserToApi(Category category) {
		switch (category) {
		case MOTOGP:
			return "MotoGP";
		case MOTO3:
			return "Moto3";
		case MOTO2:
			return "Moto2";
		default:
			return "";
		}
	}

}
