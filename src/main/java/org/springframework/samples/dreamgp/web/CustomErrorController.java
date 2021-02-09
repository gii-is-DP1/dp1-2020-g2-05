package org.springframework.samples.dreamgp.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.dreamgp.service.AuthoritiesService;
import org.springframework.samples.dreamgp.service.exceptions.DuplicatedTeamNameException;
import org.springframework.samples.dreamgp.service.exceptions.JoinWithoutCodeException;
import org.springframework.samples.dreamgp.service.exceptions.LeagueHasMaximumNumberOfTeamsException;
import org.springframework.samples.dreamgp.service.exceptions.MaximumNumberOfLeaguesPerUserException;
import org.springframework.samples.dreamgp.service.exceptions.NoLeagueFoundException;
import org.springframework.samples.dreamgp.service.exceptions.NoTeamInThisLeagueException;
import org.springframework.samples.dreamgp.service.exceptions.NotTeamUserException;
import org.springframework.samples.dreamgp.service.exceptions.NotYourTeamException;
import org.springframework.samples.dreamgp.service.exceptions.YouAlreadyParticipateInALeagueException;
import org.springframework.samples.dreamgp.service.exceptions.duplicatedLeagueNameException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/error")
@Slf4j
@ControllerAdvice
public class CustomErrorController implements ErrorController {
	
	@Autowired
	private ErrorAttributes errorAttributes;
	@Autowired
	private AuthoritiesService authoritiesService;

	public static HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		}
		catch (Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	@ExceptionHandler(NoTeamInThisLeagueException.class)
	public String NoTeamErrorHandler(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "No tienes equipo en esta liga!");
		return "redirect:/leagues";
	}
	
	@ExceptionHandler(MaximumNumberOfLeaguesPerUserException.class)
	public String CanNotHaveMoreLeaguesHandler(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "You already participate in 5 leagues!");
		return "redirect:/leagues/myLeagues";
	}
	
	@ExceptionHandler(NoLeagueFoundException.class)
	public String NoLeagueFoundHandler(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "No league have been found with the code provided");
		return "redirect:/leagues/join";
	}
	
	@ExceptionHandler(YouAlreadyParticipateInALeagueException.class)
	public String AlreadyParticipateInThatLeagueHandler(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "You already participates in that league!");
		return "redirect:/leagues/join";
	}

	@ExceptionHandler(LeagueHasMaximumNumberOfTeamsException.class)
	public String LeagueHasMaxTeams(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "This league is currently full !");
		return "redirect:/leagues/join";
	}

	
	@ExceptionHandler(duplicatedLeagueNameException.class)
	public String DuplicateNameLeagueHandler(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "Already exist a league with that name! :(  ");
		return "redirect:/leagues/new";
	}
	
	@ExceptionHandler(JoinWithoutCodeException.class)
	public String joinWithoutCodeErrorHandler(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "Necesitas un código de invitación para entrar en una liga");
		return "redirect:/leagues";
	}
	

	@ExceptionHandler(NotTeamUserException.class)
	public String NotUserTeam(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "You are not the Owner of this team!");
		return "redirect:/myTeams";
	}
	
	@ExceptionHandler(DuplicatedTeamNameException.class)
	public String DuplicatedTeamNameException(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "Already exists a team with this name");
		return "redirect:/leagues/{leagueId}/teams";
	}

	@ExceptionHandler(NotYourTeamException.class)
	public String NotYourTeamExceptionHandler(HttpServletRequest request,  Exception ex, RedirectAttributes redirectAttributes)  {
		redirectAttributes.addFlashAttribute("message", "Este no es tu equipo!");
		return "redirect:/leagues";
	}
	
	public String defaultErrorHandler(HttpServletRequest request,  Exception ex)  {

		ServletWebRequest servletWebRequest = new ServletWebRequest(request);
		HttpStatus status = getStatus(request);
		ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<Map<String,Object>>(status);
		if (status == HttpStatus.NO_CONTENT) {
			responseEntity = new ResponseEntity<>(status);
		}
		Map<String, Object> body = this.errorAttributes.getErrorAttributes(servletWebRequest, true);
		responseEntity = new ResponseEntity<>(body, status);

		Map<String, Object> atributos = responseEntity.getBody();
		List<String> keys = atributos.keySet().stream().collect(Collectors.toList());

		request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
		request.setAttribute("javax.servlet.error.status_code", 400);
		request.setAttribute("exception", ex);
		request.setAttribute("timestamp", atributos.get(keys.get(0)));
		request.setAttribute("error", atributos.get(keys.get(1)));
		request.setAttribute("status", atributos.get(keys.get(2)));
		request.setAttribute("error_message", atributos.get(keys.get(3)));
		request.setAttribute("trace", atributos.get(keys.get(4)));

		return "errorAdmin";
	}
	
	@RequestMapping
	@ExceptionHandler(Exception.class)
	public String handleError(HttpServletRequest request, Exception ex) {
		String res = "errorUser";
		if (this.authoritiesService.isCurrentUserAdmin()) {
			res = defaultErrorHandler(request, ex);
		} else {
			Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
			log.error("HTTP ERROR STATUS: " + status);
			if (status != null) {
				int statusCode = Integer.parseInt(status.toString());
				
				switch (statusCode) {
				case 400: 
					request.setAttribute("error_message", "Error 400: You made a bad request");
					break;
				case 401: 
					request.setAttribute("error_message", "Error 401: You're not authorized to access this content!");
					break;
				case 403: 
					request.setAttribute("error_message", "Http Error Code: 403. Forbidden");
					res = "errors/error403";
					break;
				case 404: 
					request.setAttribute("error_message", "Http Error Code: 404. Resource not found");
					res = "errors/error404";
					break;
				case 500: 
					request.setAttribute("error_message", "Http Error Code: 500. Internal Server Error");
					res = "errors/error500";
					break;
				default: 
					res = "errorUser";
					request.setAttribute("error_mensaje", "¡Oops! Looks like something went wrong...");
				}
			}
		}
		return res;
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
