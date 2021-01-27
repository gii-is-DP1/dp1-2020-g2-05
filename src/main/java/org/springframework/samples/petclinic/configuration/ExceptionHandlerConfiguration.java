package org.springframework.samples.petclinic.configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration 
{
	@Autowired
//	private CustomErrorController customErrorController;
	private BasicErrorController basicErrorController;
    // add any exceptions/validations/binding problems

   @ExceptionHandler(Exception.class)
   public String defaultErrorHandler(HttpServletRequest request,  Exception ex)  {
	   
	   Map<String, Object> atributos= basicErrorController.error(request).getBody();
       List<String> keys = atributos.keySet().stream().collect(Collectors.toList());
       
        request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
        request.setAttribute("javax.servlet.error.status_code", 400);
        request.setAttribute("exception", ex); //Habia una errata, ponia "exeption"
        request.setAttribute("timestamp", atributos.get(keys.get(0)));
        request.setAttribute("error", atributos.get(keys.get(1)));
        request.setAttribute("status", atributos.get(keys.get(2)));
        request.setAttribute("message", atributos.get(keys.get(3)));
        request.setAttribute("trace", atributos.get(keys.get(4)));

        return "error";
    }
}