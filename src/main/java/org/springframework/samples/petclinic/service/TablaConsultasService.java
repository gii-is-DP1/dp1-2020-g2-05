package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.repository.TablaConsultasRepository;
import org.springframework.samples.petclinic.web.LeagueController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TablaConsultasService {

	
	
	@Autowired
	TablaConsultasRepository TCRepository;
	
	
	
	public Optional<TablaConsultas> getTabla() throws DataAccessException {
		return TCRepository.findById(1);
	}
	
	@Modifying
	public void actualizarTabla(Category category) throws DataAccessException {
		TablaConsultas tabla = this.getTabla().get();
		if(category.toString().equals(tabla.getCurrentCategory().toString())) {
			//para que no se aumente en +3 las carreras, para eso
			//comprobamos si la categoria pasada como
			//parametro es la categoria actual y aumentamos +1
			log.debug("CARRERAS AUMENTADAS : "+tabla.getRacesCompleted()+"->"+(tabla.getRacesCompleted()+1));
		tabla.setRacesCompleted(tabla.getRacesCompleted()+1);
		tabla.setActualRace(tabla.getRacesCompleted()+1);
	
		
			if(tabla.getRacesCompleted()<10) {
				tabla.setCurrentCategory(Category.MOTO3); //activar moto3 si las carreras son > que 10
				log.info("TABLA CONSULTA SET CATEGORY:" + Category.MOTO3  );

			}
		else if(tabla.getRacesCompleted()>=10 && tabla.getRacesCompleted()<15 ) {
				tabla.setCurrentCategory(Category.MOTO2);	 //activar moto2 si las carreras son >= que 10 y < 15
				log.info("TABLA CONSULTA SET CATEGORY:" + Category.MOTO2  );

			}
		else if(tabla.getRacesCompleted()>=15 ) {
			tabla.setCurrentCategory(Category.MOTOGP); //activar motogp si las carreras son >= 15
			log.info("TABLA CONSULTA SET CATEGORY:" + Category.MOTOGP  );

			}
		if(tabla.getRacesCompleted()>20) {
			tabla.setCurrentCategory(Category.MOTOGP); 
			log.info("TABLA CONSULTA SET CATEGORY:" + Category.MOTOGP  );
			tabla.setRacesCompleted(20);
			tabla.setActualRace(20);
			}	
		log.info("TABLA CONSULTA ACTUALIZADA CORRECTAMENTE");

		}
		
		this.saveTabla(tabla);

	}
	
	
	public void saveTabla(TablaConsultas tabla) throws DataAccessException {
		 TCRepository.save(tabla);
	}
	
	public boolean checkDates(TablaConsultas tabla) {
		if(tabla.getTimeMessage()=="" ||tabla.getTimeMessage()==null ) {
			return false;
		}
		//metodo para checkear si hoy y la hora de hoy es superior a 
		//timeMessage. Si se cumple devuelve false por lo que se puede usar
		//para mensajes temporales
		LocalDate hoy = LocalDate.now();
		LocalDate fechaExpiracion = LocalDate.parse(tabla.getTimeMessage().split(",")[0]);
		if(fechaExpiracion.isAfter(hoy)) {
			return true;
		}else if(fechaExpiracion.isEqual(hoy)) {
			LocalTime ahora = LocalTime.now();
			LocalTime timeExpiracion = LocalTime.parse(tabla.getTimeMessage().split(",")[1]);
			if(ahora.isBefore(timeExpiracion)) {
				return true;
			}
		}
		return false;
	}
	
}
