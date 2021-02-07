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
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.web.validator.MessageValidator;
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

import lombok.extern.slf4j.Slf4j;




/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Slf4j
@Controller
public class MessageController {



	MessageService messageService;
	

	UserService userService;
	

	@InitBinder("message")
	public void initMessageBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new MessageValidator());
	}

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
		log.info("Listado de mensajes cargado correctamente");

		return "messages/messagesList";
	}
	
	@GetMapping(path="messages/delete/{messageId}")
	public String borrarMensaje(@PathVariable("messageId") int messageId, ModelMap model,RedirectAttributes ra) {
		Optional<Message> message = messageService.findMessageById(messageId);
		if(message.isPresent()) {
			messageService.delete(message.get());
			ra.addAttribute("message", "message successfully deleted!");
			log.info("El mensaje"+message+" se ha eliminado correctamente");

		}else {
			log.info("El mensaje"+message+" no se ha podido eliminar");

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
		log.info("Inicializado formulario correctamente");

		return "messages/messagesEdit";
	}
	
	@GetMapping(path="/messages/new/{username}")
	public String crearMensajePredefinido(@PathVariable("username") String username,ModelMap model) {
		Message message = new Message();
		try {
			Optional<User> usuariorecibe = userService.findUser(username);
			message.setUsernamereceive(usuariorecibe.get());

		} catch (Exception e) {
			log.warn("No se ha encontrado el usuario");
			return "errors/exception";
		}
		
		
		message.setUsernamesend(userService.getUserSession());
		message.setVisto(0);
		model.put("messagee", message);		
		log.info("Inicializado formulario correctamente");

		return "messages/messagesEdit";
	}

	
	
	@PostMapping(value = "/messages/new")
	public String processCreationForm(@Valid Message message, BindingResult result,ModelMap model) {
	
		
		if (result.hasErrors()) {
			List<ObjectError> errores = result.getAllErrors();
			List<String> erroresstring = new ArrayList<String>();
			for(int i=0;i<errores.size();i++) {
				erroresstring.add(errores.get(i).getDefaultMessage());
			}
			model.put("message",erroresstring );	
			model.put("messagee", message);		
			model.put("usersend", userService.getUserSession());		
			log.info("El mensaje"+message+" no se ha podido guardar correctamente");

			return "messages/messagesEdit";
		}
		else {
			
				
			message.setVisto(0); 
			message.setUsernamesend(userService.getUserSession());

			this.messageService.saveMessage(message);
			log.info("El mensaje"+message+" se ha guardado correctamente");

			return "redirect:/messages/";
		}
	}
	
	@PostMapping(value = "/messages/new/{username}")
	public String processCreationFormPredefinido(@Valid Message message, BindingResult result,ModelMap model) {
		
		if (result.hasErrors()) {
			List<ObjectError> errores = result.getAllErrors();
			List<String> erroresstring = new ArrayList<String>();
			for(int i=0;i<errores.size();i++) {
				erroresstring.add(errores.get(i).getDefaultMessage());
			}
			model.put("message",erroresstring );	
			model.put("messagee", message);		
			model.put("usersend", userService.getUserSession());		
			log.info("El mensaje"+message+" no se ha podido guardar correctamente");

			return "messages/messagesEdit";
		}
		else {
			message.setVisto(0); 
			message.setUsernamesend(userService.getUserSession());

			this.messageService.saveMessage(message);
			log.info("El mensaje"+message+" se ha guardado correctamente");

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
	

	


	
}

