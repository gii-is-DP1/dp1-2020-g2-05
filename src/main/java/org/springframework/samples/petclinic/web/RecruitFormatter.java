package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.stereotype.Component;

@Component
public class RecruitFormatter implements Formatter<Recruit> {
	
	private final RecruitService recruitService;

	@Autowired
	public RecruitFormatter(RecruitService recruitService) {
		this.recruitService = recruitService;
	}

	@Override
	public String print(Recruit recruit, Locale locale) {
		return recruit.getPilot().getName() + " " + recruit.getPilot().getLastName();
	}

	@Override
	public Recruit parse(String text, Locale locale) throws ParseException {
		List<Pilot> findRecruits = this.recruitService.getRecruits();
		for (Pilot pilot : findRecruits) {
			String string = pilot.getName() + " " + pilot.getLastName();
			if (string.equals(text)) {
				Recruit recruit = this.recruitService.getRecruitByPilotId(pilot.getId()).get();
				return recruit;
			}
		}
		throw new ParseException("Recruit not found: " + text, 0);
	}

}
