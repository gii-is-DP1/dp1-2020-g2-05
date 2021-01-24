package org.springframework.samples.petclinic.service;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.ResultRepository;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ResultService {
	private GranPremioService gpService;
	private LineupService lineupService;

	private TeamRepository TR;
	private ResultRepository resultRepository;
	private TablaConsultasService TCService;
	
	@Autowired
	public ResultService(ResultRepository resultRepository, TablaConsultasService TCService,TeamRepository TR,GranPremioService gpService,
			LineupService lineupService) {
		this.resultRepository = resultRepository;
		this.TCService=TCService;
		this.TR=TR;
		this.gpService = gpService;
		this.lineupService = lineupService;
	}

//	@Transactional(readOnly = true)
//	public Collection<Result> findResults(String name) throws DataAccessException {
//		return resultRepository.findResults(name);
//	}
//	
	@Transactional
	public void saveResult(Result result) throws DataAccessException {
		resultRepository.save(result);
	}

	@Transactional(readOnly = true)
	public Optional<Result> findResultById(int id) throws DataAccessException {
		return resultRepository.findById(id);
	}

//	@Transactional(rollbackFor = DuplicatedPetNameException.class)
//	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
//			Pet otherPet=pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
//            if (StringUtils.hasLength(pet.getName()) &&  (otherPet!= null && otherPet.getId()!=pet.getId())) {            	
//            	throw new DuplicatedPetNameException();
//            }else
//                petRepository.save(pet);                
//	}
	@Transactional
	public List<Result> findResultsByCategoryAndId(Integer gpId, String raceCode,Category category) throws DataAccessException {
		return resultRepository.findResultsByCategoryAndId(gpId, raceCode,category);
	}
	
	
	public List<Result> findAll() {
		return (List<Result>) resultRepository.findAll();
	}
	
	@Transactional
	@Modifying
	public void validateResults() {
		
		Integer gpId = gpService.ultimoGPSinValidar().getId();
		String code =gpService.findGPById(gpId).get().getRaceCode();
		
		List<Result> results = new ArrayList<Result>();
		
		List<Result> lista = this.resultRepository.findResultsByCategoryAndId(gpId, code, Category.MOTO2);
		List<Result> lista2 = this.resultRepository.findResultsByCategoryAndId(gpId, code, Category.MOTO3);
		List<Result> lista3 = this.resultRepository.findResultsByCategoryAndId(gpId, code, Category.MOTOGP);
		
		results.addAll(lista);
		results.addAll(lista2);
		results.addAll(lista3);
		
		List<Lineup> lineups =this.lineupService.findAll();
		
		
		for(int i=0;i<results.size();i++) {
			Result resultado_i = results.get(i);
			for(int j=0;j<lineups.size();j++) {
				Lineup lineup_j = lineups.get(j);
				Team team_j = lineup_j.getTeam();
				
				if(lineup_j.getGp().getId()==gpId
						&& (lineup_j.getRider1().equals(resultado_i.getPilot()) || lineup_j.getRider2().equals(resultado_i.getPilot()))) {
					Integer puntos = this.calculaPuntos(resultado_i.getPosition())+team_j.getPoints();
					Integer money = this.calculaMoney(resultado_i.getPosition())+team_j.getMoney();
					team_j.setMoney(money);
					team_j.setPoints(puntos);
					TR.save(team_j);
					log.info("Se le ha sumado al equipo '"+team_j.getName()+"' un total de "+this.calculaPuntos(resultado_i.getPosition())+" puntos y "+this.calculaMoney(resultado_i.getPosition())+" $ correctamente");
				}
			}
		}
		
		TCService.getTabla().get().setGpsValidated(TCService.getTabla().get().getGpsValidated()+1);
		this.createTimeMessage();
	}
	
	
	public void createTimeMessage() {
		TablaConsultas tc=  TCService.getTabla().get();
		LocalDate hoy = LocalDate.now();
		LocalTime ahora = LocalTime.now();
		LocalDate mañana = hoy.plusDays(1);
		tc.setTimeMessage(mañana+","+ahora);
		this.TCService.saveTabla(tc);
		//aqui lo que se hace es establecer la propiedad time message
		//a mañana en la hora actual

	}
	
	public  Integer calculaPuntos(Integer pos) {
		switch (pos) {
		case 0:return 0;
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
		case 16:return 1;
		case 17:return 1;
		case 18:return 1;
		case 19:return 1;
		case 20:return 1;
		case 21:return 1;
		case 22:return 1;
		case 23:return 1;
		case 24:return 1;
		case 25:return 1;
		case 26:return 1;
		case 27:return 1;
		case 28:return 1;
		case 29:return 1;
		case 30:return 1;
		default:return 0;
		}
	}
	public  Integer calculaMoney(Integer pos) {
		switch (pos) {
		case 0:return 0;
		case 1:	return 2500;
		case 2:	return 2000;
		case 3:	return 1600;
		case 4:	return 1300;
		case 5:	return 1100;
		case 6:	return 1000;
		case 7:	return 900;
		case 8:	return 800;
		case 9:	return 700;
		case 10:return 600;
		case 11:return 500;
		case 12:return 400;
		case 13:return 300;
		case 14:return 200;
		case 15:return 100;
		case 16:return 100;
		case 17:return 100;
		case 18:return 100;
		case 19:return 100;
		case 20:return 100;
		case 21:return 100;
		case 22:return 100;
		case 23:return 100;
		case 24:return 100;
		case 25:return 100;
		case 26:return 100;
		case 27:return 100;
		case 28:return 100;
		case 29:return 100;
		case 30:return 100;
		default:return 0;
		}
	}
	
	
}
