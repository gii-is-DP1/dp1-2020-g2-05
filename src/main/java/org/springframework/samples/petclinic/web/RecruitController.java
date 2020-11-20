package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/recruits")
public class RecruitController {
	
	private RecruitService recruitService;
	
	@Autowired
	public RecruitController(RecruitService recruitService) {
		this.recruitService = recruitService;
	}
	
	@GetMapping("/{teamid}")
	public ModelAndView getRecruits(@PathVariable Long teamID) {
		ModelAndView mav = ModelAndView.;
		List<Recruit> fichajes = this.recruitService.getRecruits(teamID);
		
		
		
		
		return null;
		
	}
	
}
