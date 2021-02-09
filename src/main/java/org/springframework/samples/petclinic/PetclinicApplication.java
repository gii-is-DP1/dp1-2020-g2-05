package org.springframework.samples.petclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.samples.petclinic.service.PoblarBaseDeDatosService;
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
	@Autowired
	private PoblarBaseDeDatosService PBDService;

	public static void main(String[] args) {
		SpringApplication.run(PetclinicApplication.class, args);
	}

	// Ajustar para que haga una carga inicial al arrancar la aplicación.
	// Se ejecuta cada 5 minutos automáticamente
	@Scheduled(cron = "0 0/5 * * * ?")
	public void actualizarVariablesDeSistema() {
		log.info("Actualizando variables del sistema");
		TCservice.actualizarTablaAutomatica();
	}

	// Ajustar a lunes (0 00 00 ? * MON)
	@Scheduled(cron = "0 37 02 ? * *")
	public void validarCarreras() throws Exception {
		log.info("Validando la ultima carrera completada");
		System.out.println("VALIDANDO");

        TCservice.comprobandoCarrerasCompletadas();
    }
	
	//Ajustar a Domingo (0 00 00 ? * 7)
	@Scheduled(cron = "0 36 02 ? * *") 
    public void PoblarUltimaCarrera() throws Exception {
		log.info("Poblando la ultima carrera completada");
		System.out.println("Poblando");
		this.PBDService.poblandoUltimaCarreraCompletada();
		
    }

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}
	

	
	

}
