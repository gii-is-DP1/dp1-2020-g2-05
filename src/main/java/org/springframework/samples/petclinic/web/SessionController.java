package org.springframework.samples.petclinic.web;

import java.util.Optional;

import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionController {
	
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


}
