package hello.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hello.model.Greeting;

@RestController
@RequestMapping("/session")
public class SessionController {

//	@GetMapping(value = "/login", produces = "application/json")
	@GetMapping("/login")
	public Greeting helloUser(Principal principal) {
//		HashMap<String, String> result = new HashMap<String, String>();
//		result.put("username", principal.getName());
		return new Greeting(0L, principal.getName());
	}
	
	@GetMapping("/home")
	public Greeting home(HttpSession session) {
		return new Greeting(0L, session.getId());
	}

	@GetMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
