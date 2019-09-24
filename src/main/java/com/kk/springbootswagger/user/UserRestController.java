package com.kk.springbootswagger.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;


@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user")
	public ResponseEntity<User> getUser(@RequestParam String username) {
		if(StringUtils.isEmpty(username)) 
			return ResponseEntity.badRequest().build();
		User user =userRepository.findByUsername(username);
		return ResponseEntity.ok(user);
		
	}
	
	
	@PostMapping("/user")
	public ResponseEntity<Void> createUser(@RequestBody User user ,BindingResult errors , HttpServletRequest request){
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().build();			
		}
		User user1 = userRepository.save(user);
		URI location = new UriTemplate(request.getRequestURI() + "/{id}").expand(user1.getId());
		return ResponseEntity.created(location).build();
	}
}
