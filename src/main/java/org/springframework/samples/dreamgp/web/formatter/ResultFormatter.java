package org.springframework.samples.dreamgp.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.dreamgp.model.Result;
import org.springframework.samples.dreamgp.service.ResultService;
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
		return "Location : " + result.getGp().getCircuit() + ", Circuit : " + result.getGp().getSite() + ", Position : " + result.getPosition();
	}

	@Override
	public Result parse(String text, Locale locale) throws ParseException {
		return null;
	}

}
