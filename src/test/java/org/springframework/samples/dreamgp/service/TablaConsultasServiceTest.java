package org.springframework.samples.dreamgp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.TablaConsultas;
import org.springframework.samples.dreamgp.service.TablaConsultasService;
import org.springframework.stereotype.Service;

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
		TC.setRacesCompleted(7);
		this.TCService.actualizarTabla(TC.getCurrentCategory());
		TC = this.TCService.getTabla().get();
		assertThat(cat).isEqualTo(TC.getCurrentCategory());
	}

	@Test
	void shouldUpdateTablaToMotoGP() {
		Category cat = Category.MOTOGP;
		TablaConsultas TC = this.TCService.getTabla().get();
		TC.setCurrentCategory(cat);
		TC.setRacesCompleted(25);
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
