package org.springframework.samples.dreamgp;

import java.text.ParseException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.service.LineupService;
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
		
		@Autowired
		private LineupService lineupService;
		
		Integer lineupsAntiguos;
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
		
		@Scheduled(cron = "0 00 00 ? * 7")
		public void bloquearLineups() throws ParseException {
				List<GranPremio> gps = this.PBDService.findAllActualYear(2019);
				Integer gpsCompleted = this.TCservice.getTabla().get().getRacesCompleted();
				GranPremio gp = gps.get(gpsCompleted);
				gp.setHasBeenRun(true);
			}
		
		@Scheduled(cron = "0 00 00 ? * *") // Se ejecuta todos los dias a las 00:00
		public void estadisticasLineups() throws Exception {
			log.info("Calculando estadisticas de alineaciones");
			
			List<Integer> estadisticas = lineupService.calculaEstadisticas(lineupsAntiguos);
			if (estadisticas.get(0) == 0) { // Codigo que indica que no ha cambiado el numero de lineups totales
				log.info("El numero de alineaciones que hay en nuestra pagina no ha cambiado en todo el día.");
			} else if(estadisticas.get(0) == 1) { // Codigo que indica que ha aumentado el numero de lineups totales
				log.info("Hoy hay " + estadisticas.get(1) + " alineaciones mas que ayer en nuestra pagina.");
			} else if(estadisticas.get(0)== 2) { // Codigo que indica que ha disminuido el numero de lineups totales
				log.info("Hoy, el numero de alineaciones que hay en nuestra pagina ha bajado en " + estadisticas.get(1) + ".");
			}
			log.info("Estadisticas de alineaciones calculadas");
			lineupsAntiguos =  estadisticas.get(2); // Lineups actuales
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
