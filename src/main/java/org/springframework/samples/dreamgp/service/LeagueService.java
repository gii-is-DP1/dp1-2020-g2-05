package org.springframework.samples.dreamgp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.League;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.repository.LeagueRepository;
import org.springframework.samples.dreamgp.repository.UserRepository;
import org.springframework.samples.dreamgp.service.exceptions.duplicatedLeagueNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class LeagueService {
	
	private LeagueRepository leagueRepository;

	@Autowired
	public LeagueService(LeagueRepository leagueRepository) {
		this.leagueRepository = leagueRepository;
	}

	@Transactional
	public Boolean saveLeague(League ligaAGuardar) throws DataAccessException, duplicatedLeagueNameException {
		log.info("Intentando guardar liga : " + ligaAGuardar);
		List<League> todasLasLigas = this.findAll();
		for (int i = 0; i < todasLasLigas.size(); i++) {
			if (todasLasLigas.get(i).getName().equals(ligaAGuardar.getName())) {
				log.warn("No se ha podido guardar la liga " + ligaAGuardar);
				throw new duplicatedLeagueNameException("Duplicated league name");
			}
		}
		log.info("La liga '" + ligaAGuardar + "' se ha guardado correctamente");
		leagueRepository.save(ligaAGuardar);
		return true;
	}
	
	@Transactional
	public void deleteLeague(League league) throws DataAccessException {
		leagueRepository.delete(league);
	}
	 
	@Transactional(readOnly = true)
	public Optional<League> findLeagueByLeagueCode(String leagueCode) throws DataAccessException {
		return leagueRepository.findByLeagueCode(leagueCode);
	}

	@Transactional(readOnly = true)
	public Integer findLeaguesByUsername(String username) throws DataAccessException {
		return leagueRepository.findLeaguesByUsername(username);
	}

	public String randomString(int longitud) {
		log.info("Autogenerando codigo para una liga");
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		String sb = "";
		Random random = new Random();

		for (int i = 0; i < longitud; i++) {

			int index = random.nextInt(alphabet.length());

			char randomChar = alphabet.charAt(index);

			sb += (randomChar);
		}
		return sb;
	}

	@Transactional
	public boolean comprobarSiHayLigasVacias(List<League> todasLasLigas) {
		Boolean resultado = false;

		for (League liga_i : todasLasLigas) {
			Set<Team> equiposDeLaLiga_i = liga_i.getTeam();
			if (equiposDeLaLiga_i.size() == 1) {
				if (equiposDeLaLiga_i.stream().collect(Collectors.toList()).get(0).getName().equals("Sistema")) {
					this.deleteLeague(liga_i);
					resultado = true;
					log.warn("Se ha detectado una liga sin equipos : " + liga_i);

				}
			}else if(equiposDeLaLiga_i.isEmpty()) {
				this.deleteLeague(liga_i);
				resultado = true;
				log.warn("Se ha detectado una liga sin equipos : " + liga_i);
			}

		}
		return resultado;
	}
	
	public List<League> obtenerListaDeLigasDeUnaListaDeIntegers(Collection<Integer> listaIntegers) {

		List<League> listaLigas = listaIntegers.stream().map(integer_i -> this.findLeague(integer_i).get()).collect(Collectors.toList());

		return listaLigas;
	}

	public List<League> findAll() {
		List<League> listaReturn = new ArrayList<>();
		leagueRepository.findAll().forEach(listaReturn::add);
		return  listaReturn;
	}

	public Optional<League> findLeague(Integer leagueId) {
		return leagueRepository.findById(leagueId);
	}

	
	public ModelMap descifraUri(String stringADescifrar,String code,ModelMap model) {
		//dependiendo del path descifro lo que la api no ha podido encontrar
		// el index 0 significa cuantos no ha podido encontrar(1 a 3)
		//los sigiuentes significan, 3 moto3,2 moto2,GP motoGP
		//por ejemplo el path 12 significa que no ha encontrado un resultado y que es moto2
		// el path 23G que no ha encontrado 2 y que es moto3 y moto gp
		//el path  332G que no ha encontrado 3 y que son moto3,moto2 y motogp
		//...	
		if(stringADescifrar.charAt(0)=='1'){
			if(stringADescifrar.charAt(1)=='3') {
				model.addAttribute("messageMoto3NotFound",
						"API has not found any result to code " + code + " for moto3");
			}else if(stringADescifrar.charAt(1)=='2') {
				model.addAttribute("messageMoto2NotFound",
						"API has not found any result to code " + code + " for moto2");
			}else if(stringADescifrar.charAt(1)=='G') {
				model.addAttribute("messageMotogpNotFound",
						"API has not found any result to code " + code + " for motogp");
			}
		}else if(stringADescifrar.charAt(0)=='2') {
			if(stringADescifrar.charAt(1)=='3' && stringADescifrar.charAt(2)=='2') {
				model.addAttribute("messageMoto3NotFound",
						"API has not found any result to code " + code + " for moto3");
				model.addAttribute("messageMoto2NotFound",
						"API has not found any result to code " + code + " for moto2");
			}else if(stringADescifrar.charAt(1)=='3' && stringADescifrar.charAt(2)=='G') {
				model.addAttribute("messageMoto3NotFound",
						"API has not found any result to code " + code + " for moto3");
				model.addAttribute("messageMotogpNotFound",
						"API has not found any result to code " + code + " for motogp");
			}else if(stringADescifrar.charAt(1)=='2' && stringADescifrar.charAt(2)=='G') {
				model.addAttribute("messageMoto2NotFound",
						"API has not found any result to code " + code + " for moto2");
				model.addAttribute("messageMotogpNotFound",
						"API has not found any result to code " + code + " for motogp");
			}
		}else if(stringADescifrar.charAt(0)=='3') {
			model.addAttribute("messageMoto2NotFound",
					"API has not found any result to code " + code + " for moto2");
			model.addAttribute("messageMotogpNotFound",
					"API has not found any result to code " + code + " for motogp");
			model.addAttribute("messageMoto3NotFound",
					"API has not found any result to code " + code + " for moto3");
		}
		return model;
	}
	
	public ModelMap calculaCarrerasParaSiguienteCategoria(ModelMap modelMap,Category categoriaActual) {
		switch (categoriaActual) {
		case MOTO3:
			modelMap.addAttribute("carrerasParaSiguienteCategoria", 5);
			break;
		case MOTO2:
			modelMap.addAttribute("carrerasParaSiguienteCategoria", 10);
			break;
		case MOTOGP:
			modelMap.addAttribute("carrerasParaSiguienteCategoria", 19);
			break;
		default:
			modelMap.addAttribute("carrerasParaSiguienteCategoria",0);
		}
		
		switch (categoriaActual) {
		case MOTO3:
			modelMap.addAttribute("siguienteCategoria"," Moto 2 ");
			break;
		case MOTO2:
			modelMap.addAttribute("siguienteCategoria", " Moto GP ");
			break;
		case MOTOGP:
			modelMap.addAttribute("siguienteCategoria", " end season ");
			break;
		default:
			modelMap.addAttribute("siguienteCategoria","");
		}
		
		return modelMap;
	}
}