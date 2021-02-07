package org.springframework.samples.petclinic.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.service.ResultService;
import org.springframework.stereotype.Component;

@Component
public class ResultFormatter implements Formatter<Result> {
	
	private final ResultService resultService;

	@Autowired
	public ResultFormatter(ResultService resultService) {
		this.resultService = resultService;
	}

	@Override
	public String print(Result result, Locale locale) {
//		Pilot rider1 = lineup.getRider1();
//		Pilot rider2 = lineup.getRider2();
//		return rider1.getName() + " " + rider1.getLastName() + ", " + rider2.getName() + " " + rider2.getLastName();

		return "Location : " + result.getGp().getCircuit() + ", Circuit : " + result.getGp().getSite() + ", Position : " + result.getPosition();
	}

	@Override
	public Result parse(String text, Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Lineup parse(String text, Locale locale) throws ParseException {
//		List<Lineup> findLineups = StreamSupport.stream(this.lineupService.findAll().spliterator(), false).collect(Collectors.toList());
//		for (Lineup lineup : findLineups) {
//			String string = lineup.getRider1().getName() + lineup.getRider2().getName();//+","+lineup.getTeam().getName();
//			if (string.equals(text)) {
//				return lineup;
//			}
//		}
//		throw new ParseException("Lineup not found: " + text, 0);
//	}

}
