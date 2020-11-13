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
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.VetService;
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
	
	@GetMapping("/messages")
	public String listadoMensajes(ModelMap modelMap) {
		modelMap.addAttribute("resultados", messageService.findAll());
		return "messages/messagesList";
	}
	
//	@GetMapping(path="messages/{messageId}")
//	public String muestraMensajePorId(@PathVariable("messageId") int messageId, ModelMap model) {
//		Optional<Message> message = messageService.findMessageById(messageId);
//		if(message.isPresent()) {
//			model.addAttribute("message", message.get());
//		}else {
//			model.addAttribute("encontrado", false);
//		}
//		System.out.println("El id del mensaje es: " + this.messageService.findMessageById(messageId).get());
//		return "message/messageDetails";
//	}
//	
////	@GetMapping(path="/messages/{messageId}")
////	public ModelAndView muestraMensajePorId(@PathVariable("messageId") int messageId) {
////		ModelAndView mav = new ModelAndView("messages/messageDetails");
////		mav.addObject(this.messageService.findMessageById(messageId));
////		return mav;
////	}
//	
//	@GetMapping(path="/messages/new")
//	public String crearMensaje(ModelMap model) {
//		model.addAttribute("message", new Message());
//		return "messages/messagesEdit";
//	}
//	 
//	@PostMapping(path="messages/save")
//	public String guardarMensaje(@Valid Message message, BindingResult result, ModelMap model) {
//		String view = "messages/messagesList";
//		if(result.hasErrors()) {
//			model.addAttribute("message", message);
//			return "messages/messagesEdit";
//		}else {
//			messageService.saveMessage(message);
//			model.addAttribute("message", "Message successfully saved!");
//			model.addAttribute("resultados", messageService.findAll());
//		}
//		return view;
//	}
//	
//	@GetMapping(path="messages/delete/{messageId}")
//	public String borrarMensaje(@PathVariable("messageId") int messageId, ModelMap model) {
//		Optional<Message> message = messageService.findMessageById(messageId);
//		if(message.isPresent()) {
//			messageService.delete(message.get());
//			model.addAttribute("message", "message successfully deleted!");
//		}else {
//			model.addAttribute("message", "message not found!");
//		}
//		return "redirect:/messages";
//	}
//	
//	@GetMapping(path="messages/edit/{messageId}")
//	public String editarMensaje(@PathVariable("messageId") int messageId, ModelMap model) {
//		Optional<Message> message = this.messageService.findMessageById(messageId);
//		String view = "messages/messagesList";
//		model.addAttribute(message.get());
//		if(message.isPresent()) {
//			view = "messages/messagesEdit";
//			
//		}else {
//			model.addAttribute("message", "message not found!");
//			view=listadoMensajes(model);
//		}
//		return view;
//	}
//	
	
}

