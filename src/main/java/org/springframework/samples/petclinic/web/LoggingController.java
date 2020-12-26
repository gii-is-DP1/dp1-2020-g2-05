package org.springframework.samples.petclinic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoggingController {

	@GetMapping(path = "/logging")
	public @ResponseBody String logExamples() {
		log.trace("A TRACE Message");
		log.debug("A DEBUG Message");
		log.info("An INFO Message");
		log.warn("A WARN Message");
		log.error("An ERROR Message");
		
//		log.info("Debug enabled: " + log.isDebugEnabled() + "\nTrace enabled: " + log.isTraceEnabled() + "\nInfo enabled: " + log.isInfoEnabled());
		
		return "Comprueba los logs para ver la salida...";
	}
}
