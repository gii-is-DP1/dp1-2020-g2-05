package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {
	@Autowired

	UserService userService;
	LeagueService leagueService;
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	
		  
		  List<Person> persons = new ArrayList<Person>();
		  
		  Person person1 = new Person();
		  person1.setFirstName("<script> alarm('Hacked') </script>");
		  person1.setLastName("Campuzano Martínez");
		  Person person2 = new Person();
		  person2.setFirstName("Álvaro");
		  person2.setLastName("Cortes Casado");
		  Person person3 = new Person();
		  person3.setFirstName("Miguel Ángel");
		  person3.setLastName("Nieva Arjona");
		  Person person4 = new Person();
		  person4.setFirstName("Sergio");
		  person4.setLastName("Rojas Jiménez");
		  Person person5 = new Person();
		  person5.setFirstName("Alejandro");
		  person5.setLastName("Ruiz Jurado");
		  Person person6 = new Person();
		  person6.setFirstName("Mariano Manuel");
		  person6.setLastName("Torrado Sánchez");
		  
		  persons.add(person1);
		  persons.add(person2);
		  persons.add(person3);
		  persons.add(person4);
		  persons.add(person5);
		  persons.add(person6);
		  
		  model.put("persons", persons);
		  model.put("title", "DreamGP");
		  model.put("group", "G2-05");
		  
		  try {
			  Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			  Integer index1 = auth.toString().indexOf("Username:");
			  Integer index2 = auth.toString().indexOf("; Password:"); // CON ESTO TENEMOS EL STRIN Username: user
			  String nombreUsuario = auth.toString().substring(index1, index2).split(": ")[1]; //con esto hemos spliteado lo de arriba y nos hemos quedado con user.

			  Optional<User> user = this.userService.findUser(nombreUsuario);
			  
			  model.put("user", user.get());
		  }catch (Exception e) {
			// TODO: handle exception
		  }
		  
	    return "welcome";
	  }
	  
	
		

}
