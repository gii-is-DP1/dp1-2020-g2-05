package motogpAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.hibernate.internal.build.AllowSysOut;
import org.json.JSONArray;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.service.PilotService;

import motogpAPI.model.InfoCarrera;


public class Tests {
	PilotService pilotService;

	public static final String URL_RECORDS = "https://www.motogp.com/es/ajax/results/parse/2015/ARG/MotoGP/";

	public static void main(String[] args) throws Exception {

		MotoGPData data = new MotoGPData();
//
//		List<RiderOnlineData> resultado = data.getResultsByRaceNumber(Category.MotoGP, 2016, 1, Session.RACE);
//		List<RiderOnlineData> resultado2 = data.getResultsByRaceCode(Category.MotoGP, 2016, RaceCode.AUT, Session.RACE);
//		List<RiderStandingsData> resultado3 = data.getChampionshipStandings(Category.MotoGP, 2016);
//
//		System.out.println("\n Get Result By Race Number \n");
//		for (int i=0; i < resultado.size(); i++) {
//			System.out.println(resultado.get(i));
//		}
//		System.out.println("\n Get Result By Race Code \n");
//		for (int i=0; i < resultado2.size(); i++) {
//			System.out.println(resultado2.get(i));
//		}
//		System.out.println("\n Get Cahmpionship Standings \n");
//		for (int i=0; i < resultado3.size(); i++) {
//			System.out.println(resultado3.get(i));
//		}
		List<InfoCarrera> todosLosResultadosDeUnaCarrera = PeticionesGet.getResultsByRaceNumberCampu(Category.Moto2, 2012, 4, Session.RACE);
//		
//		
//
//			Record prueba = PeticionesGet.obtieneRecords("2012", Pais.SPA, Category.Moto2);
//
//			System.out.println(resultado2);
//			System.out.println(todosLosResultadosDeUnaCarrera.get(0));
//			
//		System.out.println(PeticionesGet.getResultsByRaceCodeCampu(Category.MotoGP, 2016, RaceCode.AUT, Session.RACE));
//			TreeSet<Pilot> pilotos = new TreeSet<Pilot>();
//			TreeSet<Result> results = new TreeSet<Result>();
//			
//	
//			for(int i=2015;i<2019;i++) {
//
//				for(int j=0;j<18;j++) {
//					GranPremio gp = new GranPremio(); //entidad de una carrera
//					List<InfoCarrera> todosLosResultadosDeUnaCarrera = PeticionesGet.getResultsByRaceNumberCampu(Category.MotoGP, i, j, Session.RACE);
//					
//					for(int k=0;k<todosLosResultadosDeUnaCarrera.size();k++) {
//						InfoCarrera resultado_k = todosLosResultadosDeUnaCarrera.get(k);
//						gp.setCircuit(resultado_k.getNombreEvento());
//						gp.setSite(resultado_k.getNombreEvento());
//						
//						Pilot pilot = new Pilot();
//						pilot.setName(resultado_k.getPiloto().split(" ")[0]);
//						pilot.setLastName(resultado_k.getPiloto().split(" ")[1]);
//						pilot.setDorsal(resultado_k.getNumeros().toString());
//						pilot.setNationality(resultado_k.getPais());
//						pilot.setCategory("MOTOGP");
//						Result result = new Result();
//						result.setPilot(pilot);
//						result.setPosition(resultado_k.getPosicion());
//						result.setGp(gp);
//						pilotos.add(pilot);
//						results.add(result);
//					}
//					
//					
//				}			
//			}
//			for (Pilot p:pilotos) System.out.println(p);
//			for (Result r:results) System.out.println(r);

//			RaceCode[] yourEnums = RaceCode.values();		
			
			
		
//			for(int i=0;i<infoCarrera.size();i++) {
//				System.out.println(infoCarrera.get(i));
//			}
			
			
//			List<InfoCarrera> infoCarrera = PeticionesGet.getResultsByRaceNumberCampu(Category.Moto3, 2019, 1, Session.RACE);
//			GranPremio gp = new GranPremio();
//			Set<Result> setResult = new HashSet<Result>();
//			for(int i=0;i<infoCarrera.size();i++) {
//				InfoCarrera infoCarrerafor = infoCarrera.get(i);				
//				gp.setCircuit(infoCarrerafor.getNombreEvento());
//				gp.setSite(infoCarrerafor.getNombreEvento());
//				
//				Pilot pilot = new Pilot();
//				pilot.setName(infoCarrerafor.getPiloto().split(" ")[0]);
//				pilot.setLastName(infoCarrerafor.getPiloto().split(" ")[1]);
//				pilot.setDorsal(infoCarrerafor.getNumeros().toString());
//				pilot.setNationality(infoCarrerafor.getPais());
//				Result result = new Result();
//				result.setPilot(pilot);
//				result.setPosition(infoCarrerafor.getPosicion());
//				result.setGp(gp);
//				setResult.add(result);
//				
//				
//
//			}
//			gp.setResults(setResult);
			
			
//		System.out.println(prueba.getMejorVuelta());
//		System.out.println(prueba.getRecordDelCircuito());
//		System.out.println(prueba.getVueltaDePole());
//		System.out.println(prueba.getVueltaRapida());
		
	}
	
}