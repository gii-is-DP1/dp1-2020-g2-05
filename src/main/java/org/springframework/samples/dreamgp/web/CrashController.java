/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.dreamgp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.samples.dreamgp.service.exceptions.NoTeamInThisLeagueException;

/**
 * Controller used to showcase what happens when an exception is thrown
 *
 * @author Michael Isvy
 * <p/>
 * Also see how the bean of type 'SimpleMappingExceptionResolver' has been declared inside
 * /WEB-INF/mvc-core-config.xml
 */

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
