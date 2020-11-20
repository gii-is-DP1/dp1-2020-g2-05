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




/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class MessageController {



	@Autowired
	MessageService messageService;
	
	@Autowired

	UserService userService;
	
	public User getUserSession() {
		User usuario = new User();  
		try {
			  Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			  Integer index1 = auth.toString().indexOf("Username:");
			  Integer index2 = auth.toString().indexOf("; Password:"); // CON ESTO TENEMOS EL STRIN Username: user
			  String nombreUsuario = auth.toString().substring(index1, index2).split(": ")[1]; //con esto hemos spliteado lo de arriba y nos hemos quedado con user.

			  Optional<User> user = this.userService.findUser(nombreUsuario);
			  
			  usuario =  user.get();
		  }catch (Exception e) {	
			// TODO: handle exception
		  }
		return usuario;
	}

	
	@GetMapping("/messages")
	public String listadoMensajes(ModelMap modelMap) {
		System.out.println(getUserSession().getUsername());
		modelMap.addAttribute("resultados", messageService.findAllUsernameReceive(getUserSession().getUsername()));
		return "messages/messagesList";
	}
	
	@GetMapping(path="messages/delete/{messageId}")
	public String borrarMensaje(@PathVariable("messageId") int messageId, ModelMap model) {
		Optional<Message> message = messageService.findMessageById(messageId);
		if(message.isPresent()) {
			messageService.delete(message.get());
			model.addAttribute("message", "message successfully deleted!");
		}else {
			model.addAttribute("message", "message not found!");
		}
		return "redirect:/messages";
	}
	
	@GetMapping(path="/messages/new")
	public String crearMensaje(ModelMap model) {
		Message message = new Message();
		model.put("messagee", message);		
		return "messages/messagesEdit";
	}
	
	@PostMapping(value = "/messages/new")
	public String processCreationForm(@Valid Message message, BindingResult result) {
		if (result.hasErrors()) {
			return "messages/messagesEdit";
		}
		else {
			message.setUsernamesend(getUserSession());
			message.setVisto(0); 
			//creating owner, user and authorities
			this.messageService.saveMessage(message);
			
			return "redirect:/messages/";
		}
	}
	
	@GetMapping(path="messages/view/{messageId}")
	public String muestraMensajePorId(@PathVariable("messageId") int messageId, ModelMap model) {
		Optional<Message> message = messageService.findMessageById(messageId);
		if(message.isPresent()) {
			model.addAttribute("messagee", message.get());
		}else {
			model.addAttribute("encontrado", false);
		}
		message.get().setVisto(1);
		System.out.println("El id del mensaje es: " + this.messageService.findMessageById(messageId).get());
		return "messages/messageDetails";
	}
	
////	@GetMapping(path="/messages/{messageId}")
////	public ModelAndView muestraMensajePorId(@PathVariable("messageId") int messageId) {
////		ModelAndView mav = new ModelAndView("messages/messageDetails");
////		mav.addObject(this.messageService.findMessageById(messageId));
////		return mav;
////	}
//	

	 
	@PostMapping(path="messages/save")
	public String guardarMensaje(@Valid Message message, BindingResult result, ModelMap model) {
		String view = "messages/messagesList";
		if(result.hasErrors()) {
			model.addAttribute("message", message);
			return "messages/messagesEdit";
		}else {
			System.out.println(message.getId());
			messageService.saveMessage(message);
			model.addAttribute("message", "Message successfully saved!");
			model.addAttribute("resultados", messageService.findAllUsernameReceive(getUserSession().getUsername()));
		}
		return view;
	}
	


	
}

