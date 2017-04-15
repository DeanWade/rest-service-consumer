package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

	private final RestTemplate restTemplate;

	public ConsumerController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/consumer/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name,
			@RequestParam(value = "async", defaultValue = "false") boolean async,
			@RequestParam(value = "daemon", defaultValue = "false") boolean daemon) throws Exception {
		RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
		invoker.setAsync(async);
		invoker.setDaemon(daemon);
		return invoker.greet();
	}
	
	@PostMapping("/consumer/greeting")
	public Greeting greet(@RequestBody Greeting greeting) throws Exception {
		return greeting;
	}
}
