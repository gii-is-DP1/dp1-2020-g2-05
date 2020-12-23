package org.springframework.samples.petclinic.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//public class CustomErrorController implements ErrorController {
//
//	@RequestMapping("/error")
////	@ResponseBody
//	public String handleError(HttpServletRequest request) {
//		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
////		return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
////				+ "<div>Exception Message: <b>%s</b></div><body></html>",
////				statusCode, exception==null? "N/A": exception.getMessage());
//		return "errors/error";
//	}
//
//	@Override
//	public String getErrorPath() {
//		return "error";
//	}
//
//	@RequestMapping("/errorTest")
//	public void handleRequest() {
//	    throw new RuntimeException("test exception");
//	}
//}
