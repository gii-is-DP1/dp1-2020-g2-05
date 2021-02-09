package motogpApiV2.apiCore;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import motogpApiV2.RaceCode;
import motogpApiV2.GranPremioDetails.GranPremioDetails;
import motogpApiV2.GranPremioDetails.Venue;
import motogpApiV2.Qualifying.Qualifying;
import motogpApiV2.races.Races;
import motogpApiV2.races.Schedule;
import motogpApiV2.results.Competitor;
import motogpApiV2.results.Results;
import motogpApiV2.stage.Season;
import motogpApiV2.stage.Stage;
@Slf4j

public class ApiMain {
	@Autowired
	private static RestTemplate restTemplate = new RestTemplate();
	
	private static String API_KEY;
	private static final String API_KEY_GP = "pfhv578e2d6bcyvfrsu6c7fn"; // Otras keys por si se acaban: , xvfu4d4a2vvyff8xgn5y7pse
	private static final String API_KEY_2 = "daq726c2d62r948rm76tk2vd"; // 9bu6jvb3n97egsjb9ytt6h4p
	private static final String API_KEY_3 = "sqhb8g4feemy8eregn7ss7y7"; // n2crg9kp8d7jmhdmt2bvsa4g
	
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

		//		Stage allSeasons = new ObjectMapper().readValue(getJsonFromUrl(urlToGetRequestApi), Stage.class);
		Stage allSeasons = restTemplate.getForObject(urlToGetRequestApi, Stage.class);

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

		//		Schedule schedules = new ObjectMapper().readValue(getJsonFromUrl(urlToGetRequestApi), Schedule.class);
		Schedule schedules = restTemplate.getForObject(urlToGetRequestApi, Schedule.class);

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
		
		
		String urlToGetRequestApi = rootUrl + scheduleId + urlToObtainResults + API_KEY;
		Results results = restTemplate.getForObject(urlToGetRequestApi, Results.class);

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
		TimeUnit.SECONDS.sleep(1);

//		Stage allSeasons = new ObjectMapper().readValue(getJsonFromUrl(urlToGetRequestApi), Stage.class);	
		Stage allSeasons = restTemplate.getForObject(urlToGetRequestApi, Stage.class);

		List<GranPremio> granPremiosToReturn = new ArrayList<GranPremio>();
		for(int i=0;i<allSeasons.getNumOfSeasons();i++) {
			Season stage = allSeasons.getStages().get(i);
			String idToObtainGPDetails = stage.getId();
			String uri =rootUrl+idToObtainGPDetails+urlToObtainResults+API_KEY;
			TimeUnit.SECONDS.sleep(1);
			GranPremio gp = new GranPremio();
			GranPremioDetails detailsOfGP_i = new ObjectMapper().readValue(getJsonFromUrl(uri), GranPremioDetails.class);	
//			GranPremioDetails detailsOfGP_i = restTemplate.getForObject(urlToGetRequestApi, GranPremioDetails.class);
			
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
			gp.setIdApi(idToObtainGPDetails);
			granPremiosToReturn.add(gp);
			
			log.info("Gp creado correctamente :"+gp);
		}
		
		return granPremiosToReturn;
		
	}		
	
	public static Venue obteinDetailsFromGp(String id) throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		API_KEY=API_KEY_3;

		
			String idToObtainGPDetails = id;
			String uri =rootUrl+idToObtainGPDetails+urlToObtainResults+API_KEY;
			GranPremioDetails detailsOfGP_i = new ObjectMapper().readValue(getJsonFromUrl(uri), GranPremioDetails.class);	

	
		return detailsOfGP_i.getStage().getVenue();
		
	}		
	
	
	public static String obteinIdGridFromGp(String id) throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		API_KEY=API_KEY_3;

		
			String idToObtainGPDetails = id;
			String uri =rootUrl+idToObtainGPDetails+urlToObtainResults+API_KEY;
			GranPremioDetails detailsOfGP_i = new ObjectMapper().readValue(getJsonFromUrl(uri), GranPremioDetails.class);	

			String idToReturn="";
			
			
				System.out.println(detailsOfGP_i.getStage().getStages().get(4).getDescription());
					idToReturn=  detailsOfGP_i.getStage().getStages().get(4).getId();

				
			
				 
			return idToReturn;
		
	}		
	
	public static String obtainIdOfQualifying(String idOfGpToObtainGrid) throws JsonMappingException, JsonProcessingException, IOException{
		API_KEY=API_KEY_3;
		String uri =rootUrl+idOfGpToObtainGrid+urlToObtainResults+API_KEY;

//		String urlToGetRequestApi = rootUrl + idOfGpToObtainGrid + urlToObtainResults + API_KEY;
		GranPremioDetails detailsOfGP_i = new ObjectMapper().readValue(getJsonFromUrl(uri), GranPremioDetails.class);	

		
		String idToReturn="";
		
		for(int i=0;i<10;i++) {
			if(detailsOfGP_i.getStage().getDescription().equals("Qualifying")) {
				idToReturn=  detailsOfGP_i.getStage().getId();

			}
		}
			
		return idToReturn;
	}
	
	public static List<motogpApiV2.Qualifying.Competitor> obtainGridForAGivenYearAndRacecode(String idOfGpToObtainGrid) throws InterruptedException, JsonMappingException, JsonProcessingException, IOException{
		API_KEY=API_KEY_3;
		String idToQualifying= obteinIdGridFromGp(idOfGpToObtainGrid);
		TimeUnit.SECONDS.sleep(1);

		String urlToGetRequestApi = rootUrl + idToQualifying + urlToObtainResults + API_KEY;
		Qualifying qualification = new ObjectMapper().readValue(getJsonFromUrl(urlToGetRequestApi), Qualifying.class);	
		TimeUnit.SECONDS.sleep(1);

		return qualification.getStage().getCompetitors();
		
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
