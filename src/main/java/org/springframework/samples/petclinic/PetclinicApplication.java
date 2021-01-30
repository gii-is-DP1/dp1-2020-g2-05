package org.springframework.samples.petclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication()
@EnableScheduling
public class PetclinicApplication {

	@Autowired
	private TablaConsultasService TCservice;
	public static void main(String[] args) {
		SpringApplication.run(PetclinicApplication.class, args);
	}
	//Ajustar para que haga una carga inicial al arrancar la aplicación.
	@Scheduled(cron = "0 22 23 ? * * ")
    public void actualizarvariablesdesistema() {
		log.info("Actualizando variables del sistema" );

        TCservice.actualizarTablaAutomatica();
        
    }
	
	
	//Ajustar a lunes (0 00 00 ? * 1)
	@Scheduled(cron = "0 06 19 ? * * ")
    public void validarCarreras() throws Exception {
		log.info("Validando la ultima carrera completada" );
		System.out.println("VALIDANDO");
        TCservice.comprobandoCarrerasCompletadas();
        
    }
	

}
