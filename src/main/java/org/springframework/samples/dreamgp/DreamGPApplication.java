package org.springframework.samples.dreamgp;

import java.text.ParseException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.service.PoblarBaseDeDatosService;
import org.springframework.samples.dreamgp.service.TablaConsultasService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication()
@EnableScheduling
public class DreamGPApplication {

		@Autowired
		private TablaConsultasService TCservice;
		
		@Autowired
		private TeamService teamService;
		
		@Autowired
		private PoblarBaseDeDatosService PBDService;
		
		Integer equiposAntiguos;

		public static void main(String[] args) {
			SpringApplication.run(DreamGPApplication.class, args);
		}

		// Ajustar para que haga una carga inicial al arrancar la aplicación.
		// Se ejecuta cada 5 minutos automáticamente
		@Scheduled(cron = "0 0/5 * * * ?")
		public void actualizarVariablesDeSistema() {
			log.info("Actualizando variables del sistema");
			TCservice.actualizarTablaAutomatica();
		}

		// Ajustar a lunes (0 00 00 ? * MON)
		@Scheduled(cron = "0 0 12 ? * MON")
		public void validarCarreras() throws Exception {
			log.info("Validando la ultima carrera completada");
			System.out.println("VALIDANDO");

	        TCservice.comprobandoCarrerasCompletadas();
	    }
		
		//Ajustar a Domingo (0 00 00 ? * 7)
		@Scheduled(cron = "0 11 22 ? * *") 
	    public void PoblarUltimaCarrera() throws Exception {
			log.info("Poblando la ultima carrera completada");
			System.out.println("Poblando");
			this.PBDService.poblandoUltimaCarreraCompletada();
			
	    }
		
		@Scheduled(cron = "0 00 12 ? * SUN")
		public void VerUsoAplicación() throws Exception {
			log.info("Calculando los nuevos equipos");
			System.out.println("Calculando estadísticas de nuevos equipos");
			
			List<Integer> result = teamService.ComprobandoEquiposGuardados(equiposAntiguos);
			if(result.get(0) == 0) {
				System.out.println("El número de equipos que participan en nuestra página web no ha cambiado esta semana");
			}else if(result.get(0) == 1) {
				System.out.println("¡Esta semana se han inscrito " + result.get(1) + " equipos nuevos!");
			}else if(result.get(0)== 2) {
				System.out.println("Esta semana se han dado de baja" + result.get(1) + " equipos");
			}
			log.info("Estadisticas de equipos calculadas");

			equiposAntiguos =  teamService.ComprobandoEquiposGuardados(equiposAntiguos).get(2);
	       
	    }

}
