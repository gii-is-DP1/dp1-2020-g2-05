package org.springframework.samples.dreamgp.web.formatter;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.dreamgp.model.Lineup;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.service.LineupService;
import org.springframework.stereotype.Component;

@Component
public class LineupFormatter implements Formatter<Lineup> {
	
	private final LineupService lineupService;

	@Autowired
	public LineupFormatter(LineupService lineupService) {
		this.lineupService = lineupService;
	}

	@Override
	public String print(Lineup lineup, Locale locale) {
		Pilot rider1 = lineup.getRider1();
		Pilot rider2 = lineup.getRider2();
		return rider1.getName() + " " + rider1.getLastName() + ", " + rider2.getName() + " " + rider2.getLastName();
	}

	@Override
	public Lineup parse(String text, Locale locale) throws ParseException {
		List<Lineup> findLineups = StreamSupport.stream(this.lineupService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Lineup lineup : findLineups) {
			String string = lineup.getRider1().getName() + lineup.getRider2().getName();//+","+lineup.getTeam().getName();
			if (string.equals(text)) {
				return lineup;
			}
		}
		throw new ParseException("Lineup not found: " + text, 0);
	}

}
