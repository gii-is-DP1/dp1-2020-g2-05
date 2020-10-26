package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	
		  
		  List<Person> persons = new ArrayList<Person>();
		  
		  Person person1 = new Person();
		  person1.setFirstName("Antonio");
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
		  model.put("title", "MyCycling");
		  model.put("group", "G2-05");
		  
	    return "welcome";
	  }
}
