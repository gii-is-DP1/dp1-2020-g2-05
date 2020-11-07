package motogpAPI;

import java.util.List;


public class Tests {
	
	public static final String URL_RECORDS = "https://www.motogp.com/es/ajax/results/parse/2015/ARG/MotoGP/";

	public static void main(String[] args) throws Exception {

//		MotoGPData data = new MotoGPData();
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

		for (int i=0; i < Pais.values().length; i++) {
			Record prueba = PeticionesGet.obtieneRecords("2012", Pais.values()[i], Category.Moto2);
			System.out.println(Pais.values()[i] + " " + prueba);
		}
//		System.out.println(prueba.getMejorVuelta());
//		System.out.println(prueba.getRecordDelCircuito());
//		System.out.println(prueba.getVueltaDePole());
//		System.out.println(prueba.getVueltaRapida());
		
	}
	
}