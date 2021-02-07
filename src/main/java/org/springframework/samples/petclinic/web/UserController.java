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
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.web.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String USER_CREATE_FORM = "users/createUserForm";

	private final UserService userService;
	
	private final AuthoritiesService authoritiesService;


	@Autowired
	public UserController(UserService userService, AuthoritiesService authoritiesService) {
		this.userService = userService;
		this.authoritiesService = authoritiesService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
		dataBinder.setValidator(new UserValidator());
		
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(ModelMap model) {
		User user = new User();
		model.put("userr", user);
		return USER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return USER_CREATE_FORM;
		}
		else {
			//creating  user and authority
		
			this.userService.saveUser(user);
			
			authoritiesService.saveAuthorities(user.getUsername(), "user");

			return "redirect:/";
		}
	}
	
	@GetMapping("/friends")
	public String listadoAmigos(ModelMap modelMap) {
		User user = new User();
		user.setEmail(userService.getUserSession().getUsername());
		user.setPassword("falso");
		modelMap.addAttribute("resultados", userService.findFriendByUser(userService.getUserSession().getUsername()));
		modelMap.addAttribute("user",user);
		return "friends/friendsList";
	}
	
	@PostMapping("/friends")
	public String processAddFollower(@Valid User user,BindingResult result, ModelMap model) {
		if(user.getUsername().equals(userService.getUserSession().getUsername())) {
			ObjectError error =  new ObjectError("user", "No puedes seguirte a ti mismo");
			result.addError(error);
			
		}
		
		if(result.hasErrors()) {
			List<ObjectError> errores = result.getAllErrors();
			List<String> erroresstring = new ArrayList<String>();
			for(int i=0;i<errores.size();i++) {
				System.out.println(errores.get(i).getDefaultMessage());
				erroresstring.add(errores.get(i).getDefaultMessage());
			}
			model.put("message",erroresstring );	
			model.put("user", user);
			
			return "friends/friendsList";
		}else {
			List <User> friends1 =  userService.getUserSession().getFriends();
			Optional<User> friend = 	userService.findUser(user.getUsername());
			friends1.add(friend.get());
	
			userService.getUserSession().setFriends(friends1);
			
			userService.saveUser(userService.getUserSession());

			return "redirect:/friends";
		}
	}
	
	@GetMapping(path="/friends/remove/{username}")
	public String borrarFriend(@PathVariable("username") String username, ModelMap model,RedirectAttributes ra) {
		User session = userService.getUserSession();
		
		List <User> friends1 =  session.getFriends();
	
		friends1.removeIf(x -> x.getUsername().equals(username));
		userService.getUserSession().setFriends(friends1);
		userService.saveUser(userService.getUserSession());
		ra.addAttribute("message", "Friend successfully deleted!");

		return "redirect:/friends";
	}
	

	@GetMapping(path="/friends/{username}")
	public String seeFriend(@PathVariable("username") String username,ModelMap modelMap) {
	
		Optional<User> userr = userService.findUser(username);
		modelMap.addAttribute("user",userr.get());
		return "friends/seefriend";
	}
	

}
