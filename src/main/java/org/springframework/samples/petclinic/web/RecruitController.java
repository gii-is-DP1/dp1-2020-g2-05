package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecruitController {
	
	private RecruitService recruitService;
	
	@Autowired
	public RecruitController(RecruitService recruitService) {
		this.recruitService = recruitService;
	}
	
//	@GetMapping("/recruits/{recruitID}")
//	public ModelAndView getRecruits(@PathVariable Integer recruitID) {
//		ModelAndView mav = new ModelAndView("/details");
//		mav.addObject("resultado", this.recruitService.getRecruits(recruitID));
//		return mav;
//	}
	
	@GetMapping("/recruits/")
	public ModelAndView getRecruits() {
		ModelAndView mav = new ModelAndView("/recruit/details");
		mav.addObject("resultados", this.recruitService.getRecruits());
		return mav;
	}
	
}
