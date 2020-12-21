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
	private static Integer numGpsIniciales=0;
	private static  Boolean haSidoConsultado =false;
	
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
		Integer GPsIniciales = this.getNumeroGpsIniciales();
		String queryString = year+"-01-01";  // xxxx-01-01 , hago un select con todos los gps que sean >= a esa fecha
		return GPRepository.findAllActualYear(queryString,GPsIniciales);
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
	public Integer getNumeroGpsIniciales() {
		if(haSidoConsultado==false) {
			numGpsIniciales=this.findAll().size();
			haSidoConsultado=true;
		}
		return numGpsIniciales;
	}

	
}