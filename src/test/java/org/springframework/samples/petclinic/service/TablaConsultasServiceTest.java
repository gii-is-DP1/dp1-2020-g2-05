package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.web.duplicatedLeagueNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TablaConsultasServiceTest {
	 @Autowired
		protected TablaConsultasService TCService;
	 
	 
	 @Test
		void shouldGetTabla() {
		 	Optional<TablaConsultas> TC = this.TCService.getTabla();
			assertThat(TC.isPresent()).isTrue();
		}
	
	 @Test
		void shouldSaveTabla() {
		 	TablaConsultas TC = this.TCService.getTabla().get();
		 	Integer value = TC.getRacesCompleted();
		 	TC.setRacesCompleted(666);
		 	this.TCService.saveTabla(TC);
		 	TablaConsultas TC_updated = this.TCService.getTabla().get();
			assertThat(TC_updated.getRacesCompleted()).isEqualTo(666);
			TC_updated.setRacesCompleted(value);
		 	this.TCService.saveTabla(TC);

		}
	 
	 @Test
		void shouldUpdateTablaToMoto3() {
		 	Category cat = Category.MOTO3;
		 	TablaConsultas TC = this.TCService.getTabla().get();
		 	TC.setCurrentCategory(cat);
		 	TC.setRacesCompleted(0);
		 	this.TCService.actualizarTabla(TC.getCurrentCategory());
		 	TC = this.TCService.getTabla().get();
			assertThat(cat).isEqualTo(TC.getCurrentCategory());
		}
	 
	 @Test
		void shouldUpdateTablaToMoto2() {
		 	Category cat = Category.MOTO2;
		 	TablaConsultas TC = this.TCService.getTabla().get();
		 	TC.setCurrentCategory(cat);
		 	TC.setRacesCompleted(13);
		 	this.TCService.actualizarTabla(TC.getCurrentCategory());
		 	TC = this.TCService.getTabla().get();
			assertThat(cat).isEqualTo(TC.getCurrentCategory());
		}
	 
	 @Test
		void shouldUpdateTablaToMotoGP() {
		 	Category cat = Category.MOTOGP;
		 	TablaConsultas TC = this.TCService.getTabla().get();
		 	TC.setCurrentCategory(cat);
		 	TC.setRacesCompleted(19);
		 	this.TCService.actualizarTabla(TC.getCurrentCategory());
		 	TC = this.TCService.getTabla().get();
			assertThat(cat).isEqualTo(TC.getCurrentCategory());
		}
	 
	 @Test
		void shouldNotUpdateTablaToMotoGP() {
		 	Category cat = Category.MOTOGP;
		 	TablaConsultas TC = this.TCService.getTabla().get();
		 	TC.setCurrentCategory(Category.MOTO2);
		 	TC.setRacesCompleted(11);
		 	this.TCService.actualizarTabla(cat);
		 	TC = this.TCService.getTabla().get();
			assertThat(TC.getRacesCompleted()).isEqualTo(11);
		}
}
