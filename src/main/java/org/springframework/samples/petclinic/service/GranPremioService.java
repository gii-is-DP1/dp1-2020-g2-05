package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.repository.GranPremioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GranPremioService {

	
	@Autowired
	private GranPremioRepository GPRepository;
//	private TablaConsultasService TCService;
	@Autowired
	public GranPremioService(GranPremioRepository GPRepository,TablaConsultasService TCService) {
		this.GPRepository = GPRepository;		
//		this.TCService=TCService;
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
	public List<GranPremio> findAllActualYear(Integer year) {
//		Calendar cal= Calendar.getInstance();
//		int year= cal.get(Calendar.YEAR);
		String date = year+"-01-01";  // xxxx-01-01 , hago un select con todos los gps que sean >= a esa fecha
		List<GranPremio> GPsIniciales = this.findAllActualYear(date);

		//		for(int i=0;i<GPsIniciales.size();i++) {
//			GranPremio gpObtained = this.findAllActualYear(date,GPsIniciales.get(i).getId()).get();
//			if(!(GPsIniciales.contains(gpObtained))) {
//				GPsIniciales.add(gpObtained);
//			}
//		}
		return GPsIniciales;
	}

	public static List<List<GranPremio>> granPremiosPorCategoria(List<GranPremio> lista){
		 List<List<GranPremio>> res = new ArrayList<List<GranPremio>>();
		 List<GranPremio> motoGP = new ArrayList<GranPremio>();
		 List<GranPremio> moto2 = new ArrayList<GranPremio>();
		 List<GranPremio> moto3 = new ArrayList<GranPremio>();
		 for(int i=0;i<lista.size();i++) {
			 
			 Category category = lista.get(i).getResults().stream().collect(Collectors.toList()).get(i).getPilot().getCategory();
			 if(category.equals(Category.MOTOGP)) {
				 motoGP.add(lista.get(i));
			 }else if(category.equals(Category.MOTO2)) {
				 moto2.add(lista.get(i));
			 }else if(category.equals(Category.MOTO3)) {
				 moto3.add(lista.get(i));
			 }
		 }
		 res.add(motoGP);
		 res.add(moto2);
		 res.add(moto3);
		 return res;
	}
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
		GPRepository.save(gp);
		
		
	}
	
	@Transactional
	public List<GranPremio> findAllActualYear(String date) throws DataAccessException {
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

	
}