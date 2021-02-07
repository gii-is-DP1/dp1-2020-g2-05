package org.springframework.samples.petclinic.web.formatter;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

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
	public String print(Recruit recruit, Locale locale) { // Provisional
		return recruit.getPilot().getName() + " " + recruit.getPilot().getLastName() + ", "
				+ recruit.getTeam().getLeague().getId();
	}

	@Override
	public Recruit parse(String text, Locale locale) throws ParseException {
		List<Pilot> findRecruits = this.recruitService.getRecruits();
		for (Pilot pilot : findRecruits) {
			String string = pilot.getFullName();
			String[] split = text.split(",");
			if (string.equals(split[0].trim())) {
				Integer leagueId = Integer.parseInt(split[split.length - 1].trim()); // Provisional
				Recruit recruit = this.recruitService.getRecruitByPilotId(pilot.getId(), leagueId).get();
				return recruit;
			}
		}
		throw new ParseException("Recruit not found: " + text, 0);
	}

}
