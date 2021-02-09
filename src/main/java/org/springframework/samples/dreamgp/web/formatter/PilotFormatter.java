package org.springframework.samples.dreamgp.web.formatter;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.service.PilotService;
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
		List<Pilot> findPilots = this.pilotService.findAll();
		for (Pilot pilot : findPilots) {
			String string = pilot.getName() + pilot.getLastName();
			if (string.equals(text)) {
				return pilot;
			}
		}
		throw new ParseException("Pilot not found: " + text, 0);
	}

}
