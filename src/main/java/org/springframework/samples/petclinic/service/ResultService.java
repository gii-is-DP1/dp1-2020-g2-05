package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Pilot;
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

	private TeamRepository teamRepository;
	private ResultRepository resultRepository;
	private TablaConsultasService TCService;
	private TransactionService transactionService;

	@Autowired
	public ResultService(ResultRepository resultRepository, TablaConsultasService TCService, TeamRepository teamRepository,
			GranPremioService gpService, LineupService lineupService, TransactionService transactionService) {
		this.resultRepository = resultRepository;
		this.TCService = TCService;
		this.teamRepository = teamRepository;
		this.gpService = gpService;
		this.lineupService = lineupService;
		this.transactionService = transactionService;
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
	public List<Result> findResultsByCategoryAndId(Integer gpId, String raceCode, Category category)
			throws DataAccessException {
		return resultRepository.findResultsByCategoryAndId(gpId, raceCode, category);
	}

	public List<Result> findAll() {
		return (List<Result>) resultRepository.findAll();
	}
	
	public List<Result> findByPilotId(Integer pilotId) {
		return resultRepository.findByPilotId(pilotId);
	}
	
	@Transactional
//	@Modifying
	public void validateResults() {
		
		compruebaInactividad();
		
		Integer gpId = gpService.ultimoGPSinValidar().getId();
		GranPremio gp = gpService.findGPById(gpId).get();
		String code = gp.getRaceCode();
		
		List<Result> results = new ArrayList<Result>();
		
		List<Result> lista = this.resultRepository.findResultsByCategoryAndId(gpId, code, Category.MOTOGP);
		List<Result> lista2 = this.resultRepository.findResultsByCategoryAndId(gpId, code, Category.MOTO2);
		List<Result> lista3 = this.resultRepository.findResultsByCategoryAndId(gpId, code, Category.MOTO3);
		
		results.addAll(lista);
		results.addAll(lista2);
		results.addAll(lista3);
		
		List<Lineup> lineups = this.lineupService.findByGpId(gpId);
		
		for (int i=0; i < results.size(); i++) {
			Result resultado = results.get(i);
			for(int j=0; j < lineups.size(); j++) {
				
				Lineup lineup = lineups.get(j);
				Team team = lineup.getTeam();
				List<String> pilotosConRecords = gp.getPilotsWithRecords().stream().map(x -> x.toLowerCase()).collect(Collectors.toList());

				if (lineup.getRider1().equals(resultado.getPilot()) || lineup.getRider2().equals(resultado.getPilot())) {
					
					Pilot rider;
					if (lineup.getRider1().equals(resultado.getPilot())) rider = lineup.getRider1();
					else rider = lineup.getRider2();
						
					Integer puntos = team.getPoints();
					Integer money = this.calculaMoney(resultado.getPosition()) + team.getMoney();
					
					Integer gananciaPuntos = this.calculaPuntos(resultado.getPosition());
					Integer gananciaMoney = compruebaRachasPiloto(rider.getId());
					
					String nombrePiloto = rider.getFullName();
					
					if (pilotosConRecords.contains(nombrePiloto.toLowerCase())) {
						// Realizar la valoracion que se considere oportuna
						// Por ejemplo, +10 puntos y +2000€ independientemente del record
						gananciaPuntos += 10;
						gananciaMoney += 2000;
						log.info("El piloto " + nombrePiloto + " tiene un record!");
					}
					
					team.setPoints(puntos+gananciaPuntos);
					team.setMoney(money+gananciaMoney);
					teamRepository.save(team);
					
					log.info("El equipo '" + team.getName() + "' ahora cuenta con un total de " + 
							puntos + " (+" + gananciaPuntos + ") puntos y " + 
							money + " (+" + gananciaMoney + ")€ correctamente");
					this.transactionService.results(money, lineup_j, code);
				}
			}
		}

		TCService.getTabla().get().setGpsValidated(TCService.getTabla().get().getGpsValidated() + 1);
		this.createTimeMessage();
	}
	
	public int compruebaRachasPiloto(Integer pilotId) {
		List<Result> resultados = this.findByPilotId(pilotId);
		resultados = resultados.subList(resultados.size()-3, resultados.size());
		int bonusRacha = 0;
		int contMalaRacha = 0;
		int contBuenaRacha = 0;
		
		for (Result r: resultados) {
			Integer position = r.getPosition();
			
			if (position <= 5 && position > 0) {
				contBuenaRacha++;
			} else if(position >= 13) {
				contMalaRacha++;
			}
		}
		
		if (contBuenaRacha == 3) bonusRacha = 750;
		if (contMalaRacha == 3) bonusRacha = -750;
		return bonusRacha;
	}
	
	public void compruebaInactividad() {
		try {
			List<Team> teams = (List<Team>) this.teamRepository.findAll();
			for (Team t:teams) {
				compruebaInactividadTeam(t.getId());
			}
		} catch (Exception e) {
			log.warn("No hay suficientes lineups como para expulsar a un equipo por inactividad!");
		}
	}

	public void compruebaInactividadTeam(Integer teamId) {
		List<Lineup> lineups = this.lineupService.findByTeam(teamId);
		Integer currentGpId = this.TCService.getTabla().get().getActualRace();
		if (lineups.isEmpty() && currentGpId >= 4) {
			this.teamRepository.deleteById(teamId);
		} else if (lineups.size() == 1 && currentGpId - lineups.get(0).getGp().getId()  >= 4) {
			this.teamRepository.deleteById(teamId);
		} else {
			lineups = lineups.subList(lineups.size()-2, lineups.size());

			Integer penultimoLineupGpId = lineups.get(0).getGp().getId();
			Integer ultimoLineupGpId = lineups.get(1).getGp().getId();
			if (currentGpId - ultimoLineupGpId >= 4 || ultimoLineupGpId - penultimoLineupGpId >= 4) this.teamRepository.deleteById(teamId);
		}
	}

	
	public void createTimeMessage() {
		TablaConsultas tc = TCService.getTabla().get();
		LocalDate hoy = LocalDate.now();
		LocalTime ahora = LocalTime.now();
		LocalDate mañana = hoy.plusDays(1);
		tc.setTimeMessage(mañana + "," + ahora);
		this.TCService.saveTabla(tc);
		// aqui lo que se hace es establecer la propiedad time message
		// a mañana en la hora actual

	}

	public Integer calculaPuntos(Integer pos) {
		switch (pos) {
		case 1:
			return 25;
		case 2:
			return 20;
		case 3:
			return 16;
		case 4:
			return 13;
		case 5:
			return 11;
		case 6:
			return 10;
		case 7:
			return 9;
		case 8:
			return 8;
		case 9:
			return 7;
		case 10:
			return 6;
		case 11:
			return 5;
		case 12:
			return 4;
		case 13:
			return 3;
		case 14:
			return 2;
		case 15:
			return 1;
		default:
			return 0;
		}
	}

	public Integer calculaMoney(Integer pos) {
		switch (pos) {
		case 1:	return 3000;
		case 2:	return 2500;
		case 3:	return 2000;
		case 4:	return 1500;
		case 5:	return 1400;
		case 6:	return 1300;
		case 7:	return 1200;
		case 8:	return 1100;
		case 9:	return 1000;
		case 10:return 1000;
		case 11:return 1000;
		case 12:return 1000;
		case 13:return 1000;
		case 14:return 1000;
		case 15:return 1000;
		case 0: return 0;
		default:return 500;
		}
	}

}
