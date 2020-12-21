package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.stereotype.Component;

@Component
public class PilotFormatter implements Formatter<Pilot> {
	
	private final PilotService pilotService;

	@Autowired
	public PilotFormatter(PilotService pilotService) {
		this.pilotService = pilotService;
	}

	@Override
	public String print(Pilot pilot, Locale locale) {
		return pilot.getName() + " " + pilot.getLastName();
	}
	
	@Override
	public Pilot parse(String text, Locale locale) throws ParseException {
		List<Pilot> findPilots = StreamSupport.stream(this.pilotService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Pilot pilot : findPilots) {
			String string = pilot.getName() + pilot.getLastName();
			if (string.equals(text)) {
				return pilot;
			}
		}
		throw new ParseException("Pilot not found: " + text, 0);
	}

}
