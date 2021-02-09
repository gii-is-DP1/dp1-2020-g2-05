package org.springframework.samples.dreamgp.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.Record;
import org.springframework.samples.dreamgp.repository.GranPremioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import motogpApiV2.Pais;
import motogpApiV2.RaceCode;

@Service
public class GranPremioService {

	
	private GranPremioRepository GPRepository;
	
	@Autowired
	public GranPremioService(GranPremioRepository GPRepository) {
		this.GPRepository = GPRepository;		
	}
	
	@Transactional
	public List<GranPremio> findAll() {
		return (List<GranPremio>) GPRepository.findAll();
	}

	@Transactional
	public List<GranPremio> findAllActualYear(Integer year) throws ParseException {
		String date = year+"-01-01";  // xxxx-01-01 , hago un select con todos los gps que sean >= a esa fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateF = LocalDate.parse(date, formatter);			
		
		return this.findAllActualYear(dateF);
	}

	public void delete(GranPremio gp) {
		GPRepository.delete(gp);
	}

	
	@Transactional
	public Optional<GranPremio> findGPById(int gp) {
		return GPRepository.findById(gp);
	}

	@Transactional
	public void saveGP(GranPremio gp) throws DataAccessException {
		System.out.println(gp);
		GPRepository.save(gp);
	}
	
	@Transactional
	public List<GranPremio> findAllActualYear(LocalDate date) throws DataAccessException {
		 return GPRepository.findAllActualYear(date);
	}
	
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
	}
	
	public GranPremio ultimoGPSinValidar() {
		System.out.println(GPRepository.findUltimoGpSinValidar());
		return GPRepository.findUltimoGpSinValidar().get(0);
	}
	
	public GranPremio ultimoGpCorrido(LocalDate fecha) {
		return GPRepository.ultimoGpCompletado(fecha);
	}
}