package org.springframework.samples.petclinic.web.formatter;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.stereotype.Component;

@Component
public class GPFormatter implements Formatter<GranPremio> {
	
	private final GranPremioService granPremioService;

	@Autowired
	public GPFormatter(GranPremioService granPremioService) {
		this.granPremioService = granPremioService;
	}

	@Override
	public String print(GranPremio gp, Locale locale) {
		return gp.getDate0() + ", " + gp.getSite() + ", " + gp.getCircuit();
	}
	
	@Override
	public GranPremio parse(String text, Locale locale) throws ParseException {
		List<GranPremio> findGPs = this.granPremioService.findAll();
		for (GranPremio gp : findGPs) {
			if (gp.getId().toString().equals(text)) {
				return gp;
			}
		}
		throw new ParseException("Gp not found: " + text, 0);
	}

}
