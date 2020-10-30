package motogpAPI;

import java.util.List;

public class Tests {
	
	public static final String URL_RECORDS = "https://www.motogp.com/es/ajax/results/parse/2015/ARG/MotoGP/";

	public static void main(String[] args) throws Exception {

		MotoGPData data = new MotoGPData();

		List<RiderOnlineData> resultado = data.getResultsByRaceNumber(Category.MotoGP, 2016, 1, Session.GRID);
		List<RiderOnlineData> resultado2 = data.getResultsByRaceCode(Category.MotoGP, 2016, RaceCode.AUT, Session.GRID);
		List<RiderStandingsData> resultado3 = data.getChampionshipStandings(Category.MotoGP, 2016);

		System.out.println("\n Get Result By Race Number \n");
		for (int i=0; i < resultado.size(); i++) {
			System.out.println(resultado.get(i));
		}
		System.out.println("\n Get Result By Race Code \n");
		for (int i=0; i < resultado2.size(); i++) {
			System.out.println(resultado2.get(i));
		}
		System.out.println("\n Get Cahmpionship Standings \n");
		for (int i=0; i < resultado3.size(); i++) {
			System.out.println(resultado3.get(i));
		}
		
		System.out.println(PeticionesGet.obtieneRecords("2016", "SPA"));
	}
	
}