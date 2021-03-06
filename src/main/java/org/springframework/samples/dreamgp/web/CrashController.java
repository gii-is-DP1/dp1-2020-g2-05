package org.springframework.samples.dreamgp.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/oups")
public class CrashController {

	@RequestMapping(value = "/runtime")
	public String triggerException() {
		throw new RuntimeException("Expected: controller used to showcase what happens when an exception is thrown");
	}
	
	@RequestMapping("/arithmetic")
	public Integer triggerArithmeticException() {
		return 5/0;
//	    throw new ArithmeticException("test exception");
	}
	
	@RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden() {
        return "errors/error403";
    }
	
	@RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound() {
        return "errors/error404";
    }

	@RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError() {
        return "errors/error500";
    }
	
	@RequestMapping("/errorUser")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String errorUser() {
        return "errorUser";
    }
	
	@RequestMapping("/errorAdmin")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String errorAdmin() {
        return "errorAdmin";
    }

}
