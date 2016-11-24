package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

	private final RestTemplate restTemplate;

	public GreetingController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/consumer/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name,
			@RequestParam(value = "daemon", defaultValue = "false") boolean daemon) throws Exception {
		RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
		invoker.setAsync(true);
		invoker.setDaemon(daemon);
		return invoker.greet();
	}
}
