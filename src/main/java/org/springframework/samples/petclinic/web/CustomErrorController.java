package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

//@RestControllerAdvice
@RequestMapping("/error")
@Slf4j
@ControllerAdvice
public class CustomErrorController implements ErrorController {
	
	@Autowired
	private ErrorAttributes errorAttributes;
	
	@Autowired
	AuthoritiesService authoritiesService;

//	@RequestMapping("/error")
////	@ResponseBody
//	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//		ServletWebRequest servletWebRequest = new ServletWebRequest(request);
//		HttpStatus status = getStatus(request);
//		if (status == HttpStatus.NO_CONTENT) {
//			return new ResponseEntity<>(status);
//		}
//		Map<String, Object> body = this.errorAttributes.getErrorAttributes(servletWebRequest, true);
//		return new ResponseEntity<>(body, status);
////		final StringBuilder errorDetails = new StringBuilder();
////		errorAttributes.forEach((attribute, value) -> {
////			errorDetails.append("<tr><td>")
////			.append(attribute)
////			.append("</td><td><pre>")
////			.append(value)
////			.append("</pre></td></tr>");
////		});
////
////		return String.format("<html><head><style>td{vertical-align:top;border:solid 1px #666;}</style>"
////				+ "</head><body><h2>Error Page</h2><table>%s</table></body></html>", errorDetails.toString());
//	}
	
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
	
//	   @ExceptionHandler(Exception.class)
	@RequestMapping
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
	
	@GetMapping()
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
					request.setAttribute("error_message", "Http Error Code: 400. Bad Request");
					break;
				case 401: 
					request.setAttribute("error_message", "Http Error Code: 401. Unauthorized");
					break;
				case 403: 
					request.setAttribute("error_message", "Http Error Code: 403. Forbidden");
					res = "errors/error403";
					break;
				case 404: 
					request.setAttribute("error_message", "Http Error Code: 404. Resource not found");
					break;
				case 500: 
					request.setAttribute("error_message", "Http Error Code: 500. Internal Server Error");
					res = "errors/error500";
					break;
				}
				
			} else {
				request.setAttribute("error_mensaje", "¡Ups! Parece que algo ha ido mal...");
			}
		}
		return res;
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
