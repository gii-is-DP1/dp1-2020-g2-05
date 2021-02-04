package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Record;
import org.springframework.samples.petclinic.repository.GranPremioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import motogpAPI.Pais;
import motogpAPI.RaceCode;

@Service
public class GranPremioService {

	
	private GranPremioRepository GPRepository;
	private TablaConsultasService TCService;
	
	@Autowired
	public GranPremioService(GranPremioRepository GPRepository,TablaConsultasService TCService) {
		this.GPRepository = GPRepository;		
		this.TCService=TCService;
	}
	
	
	
	

//	@Transactional(readOnly = true)
//	public Collection<Pilot> findPilotByLastName(String lastName) throws DataAccessException {
//		return pilotRepository.findByLastName(lastName);
//	}


//	public <E> List<E> convertirIterableLista(Iterable<E> gps){
//		List<E> result = new ArrayList<E>();
//		gps.forEach(result::add);
//	    return result;
//	}
	
	@Transactional
	public List<GranPremio> findAll() {
		return (List<GranPremio>) GPRepository.findAll();
	}

	@Transactional
	public List<GranPremio> findAllActualYear(Integer year) throws ParseException {
//		Calendar cal= Calendar.getInstance();
//		int year= cal.get(Calendar.YEAR);
		String date = year+"-01-01";  // xxxx-01-01 , hago un select con todos los gps que sean >= a esa fecha
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateF = LocalDate.parse(date, formatter);			
		
		List<GranPremio> GPsIniciales = this.findAllActualYear(dateF);

		//		for(int i=0;i<GPsIniciales.size();i++) {
//			GranPremio gpObtained = this.findAllActualYear(date,GPsIniciales.get(i).getId()).get();
//			if(!(GPsIniciales.contains(gpObtained))) {
//				GPsIniciales.add(gpObtained);
//			}
//		}
		return GPsIniciales;
	}

//	public static List<List<GranPremio>> granPremiosPorCategoria(List<GranPremio> lista){
//		 List<List<GranPremio>> res = new ArrayList<List<GranPremio>>();
//		 List<GranPremio> motoGP = new ArrayList<GranPremio>();
//		 List<GranPremio> moto2 = new ArrayList<GranPremio>();
//		 List<GranPremio> moto3 = new ArrayList<GranPremio>();
//		 for(int i=0;i<lista.size();i++) {
//			 
//			 Category category = lista.get(i).getResults().stream().collect(Collectors.toList()).get(i).getPilot().getCategory();
//			 if(category.equals(Category.MOTOGP)) {
//				 motoGP.add(lista.get(i));
//			 }else if(category.equals(Category.MOTO2)) {
//				 moto2.add(lista.get(i));
//			 }else if(category.equals(Category.MOTO3)) {
//				 moto3.add(lista.get(i));
//			 }
//		 }
//		 res.add(motoGP);
//		 res.add(moto2);
//		 res.add(moto3);
//		 return res;
//	}
	
	public void delete(GranPremio gp) {
		GPRepository.delete(gp);
	}

	
	@Transactional
	public Optional<GranPremio> findGPById(int gp) {
		return GPRepository.findById(gp);
	}

//	@Transactional(readOnly = true)
//	public Collection<Pilot> findPilotByLastName(String lastName) throws DataAccessException {
//		return pilotRepository.findByLastName(lastName);
//	}
//
	@Transactional
	public void saveGP(GranPremio gp) throws DataAccessException {
		System.out.println(gp);
		GPRepository.save(gp);
		
		
	}
	
	@Transactional
	public List<GranPremio> findAllActualYear(LocalDate date) throws DataAccessException {
		 return GPRepository.findAllActualYear(date);
	}
	
//	@Transactional
//	public List<GranPremio> getNumeroGpsIniciales() {
//		if(haSidoConsultado==false) {
//			numGpsIniciales=this.findAll();
//			haSidoConsultado=true;
//		}
//		return numGpsIniciales;
//	}
	
//	public void addGranPremioToCalendar(GranPremio gp) {
//		this.numGpsIniciales.add(gp);
//	}

	public static Pais parseRaceCodeToPais(RaceCode raceCode) {
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
	
	public void populateRecord(GranPremio gp) throws IOException {
		Integer anyo = gp.getDate0().getYear();
		Pais pais = parseRaceCodeToPais(RaceCode.valueOf(gp.getRaceCode()));
		System.out.println("Anyo: " + anyo);
		System.out.println("Pais: " + pais);
		if (!pais.equals(Pais.NOTFOUND) && anyo > 2004 && anyo < 2021) {
			String urlBuilderMotoGP = "https://www.motogp.com/es/ajax/results/parse/" + anyo + "/" + pais + "/" + Category.MOTOGP + "/";
			String urlBuilderMoto2 = "https://www.motogp.com/es/ajax/results/parse/" + anyo + "/" + pais + "/" + Category.MOTO2 + "/";
			String urlBuilderMoto3 = "https://www.motogp.com/es/ajax/results/parse/" + anyo + "/" + pais + "/" + Category.MOTO3 + "/";
			gp.setRecordMotoGP(new Record(urlBuilderMotoGP));
			gp.setRecordMoto2(new Record(urlBuilderMoto2));
			gp.setRecordMoto3(new Record(urlBuilderMoto3));
		}
		//			this.GPRepository.save(gp);
		//			System.out.println("Gp_Record: " + gp.getRecord());
	}
	
	public GranPremio ultimoGPSinValidar() {
		return GPRepository.findUltimoGpSinValidar().get(0);
	}
	
}