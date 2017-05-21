package hello.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

	@GetMapping("/login")
	public String helloUser(Principal principal, HttpSession session) {
		return "Session: " + session.getId() + " created for user: " + principal.getName();
	}
	
	@GetMapping("/home")
	public String home(HttpSession session) {
		return session.getId() + " at /home";
	}

	@GetMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String logout(HttpSession session) {
		session.invalidate();
		return session.getId() + " invalidated";
	}
}
