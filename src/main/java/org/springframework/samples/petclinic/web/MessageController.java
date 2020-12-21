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


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class MessageController {



	MessageService messageService;
	

	UserService userService;
	



	@Autowired
	public MessageController(MessageService messageService, UserService userService) {
		
		this.messageService = messageService;
		this.userService = userService;
	}

	@GetMapping("/messages")
	public String listadoMensajes(ModelMap modelMap) {
		User user =userService.getUserSession();
		List<Message> probando =  messageService.findAllUsernameReceive(user.getUsername());
		modelMap.addAttribute("resultados", probando);
		return "messages/messagesList";
	}
	
	@GetMapping(path="messages/delete/{messageId}")
	public String borrarMensaje(@PathVariable("messageId") int messageId, ModelMap model,RedirectAttributes ra) {
		Optional<Message> message = messageService.findMessageById(messageId);
		if(message.isPresent()) {
			messageService.delete(message.get());
			ra.addAttribute("message", "message successfully deleted!");
		}else {
			ra.addAttribute("message", "message not found!");
		}
		return "redirect:/messages";
	}
	
	@GetMapping(path="/messages/new")
	public String crearMensaje(ModelMap model) {
		Message message = new Message();
		message.setUsernamesend(userService.getUserSession());
		message.setVisto(0);
		model.put("messagee", message);		
		model.put("usersend", userService.getUserSession());		
		
		return "messages/messagesEdit";
	}
	
	@GetMapping(path="/messages/new/{username}")
	public String crearMensajePredefinido(@PathVariable("username") String username,ModelMap model) {
		Message message = new Message();
		Optional<User> usuariorecibe = userService.findUser(username);
		message.setUsernamesend(userService.getUserSession());
		message.setUsernamereceive(usuariorecibe.get());
		message.setVisto(0);
		model.put("messagee", message);		
		return "messages/messagesEdit";
	}

	
	
	@PostMapping(value = "/messages/new")
	public String processCreationForm(@Valid Message message, BindingResult result,ModelMap model) {
		
		if (result.hasErrors()) {
			model.put("messagee", message);		
			model.put("usersend", userService.getUserSession());		

			return "messages/messagesEdit";
		}
		else {
			message.setVisto(0); 
			message.setUsernamesend(userService.getUserSession());

			this.messageService.saveMessage(message);
			
			return "redirect:/messages/";
		}
	}
	
	@PostMapping(value = "/messages/new/{username}")
	public String processCreationFormPredefinido(@Valid Message message, BindingResult result,ModelMap model) {
		
		if (result.hasErrors()) {
			model.put("messagee", message);		
			model.put("usersend", userService.getUserSession());		

			return "messages/messagesEdit";
		}
		else {
			message.setVisto(0); 
			message.setUsernamesend(userService.getUserSession());

			this.messageService.saveMessage(message);
			
			return "redirect:/messages/";
		}
	}
	
	@GetMapping(path="messages/view/{messageId}")
	public String muestraMensajePorId(@PathVariable("messageId") int messageId, ModelMap model) {
		Optional<Message> message = messageService.findMessageById(messageId);
		if(message.isPresent()) {
			model.addAttribute("messagee", message.get());
			message.get().setVisto(1);
		}else {
			model.addAttribute("encontrado", false);
		}
		
		return "messages/messageDetails";
	}
	
////	@GetMapping(path="/messages/{messageId}")
////	public ModelAndView muestraMensajePorId(@PathVariable("messageId") int messageId) {
////		ModelAndView mav = new ModelAndView("messages/messageDetails");
////		mav.addObject(this.messageService.findMessageById(messageId));
////		return mav;
////	}
//	

//	 
//	@PostMapping(path="messages/save")
//	public String guardarMensaje(@Valid Message message, BindingResult result, ModelMap model) {
//		String view = "messages/messagesList";
//		if(result.hasErrors()) {
//			model.addAttribute("messagee", message);
//			return "messages/messagesEdit";
//		}else {
//			System.out.println(message.getId());
//			messageService.saveMessage(message);
//			model.addAttribute("message", "Message successfully saved!");
//		}
//		return view;
//	}
	


	
}

