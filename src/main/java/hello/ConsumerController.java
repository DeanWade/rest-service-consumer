package hello;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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
			@RequestParam(value = "daemon", defaultValue = "false") boolean daemon){
		RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
		invoker.setAsync(async);
		invoker.setDaemon(daemon);
		invoker.setLock("sync");
		return invoker.greeting();
	}
	
	@GetMapping("/provider/greeting")
	public Greeting greeting(
    		@RequestParam(value="name", defaultValue="World") String name,
    		@RequestParam(value="lock", defaultValue="lock") String lock) {
		RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
		invoker.setAsync(false);
		invoker.setDaemon(false);
		invoker.setLock(lock);
		return invoker.greeting();
	}
	
	@ExceptionHandler(Exception.class)
	public Greeting onException(Exception e){
		Greeting greeting = new Greeting(Long.MAX_VALUE, e.getMessage());
		return greeting;
	}
	
	@GetMapping("/consumer/random")
	public Greeting randomUriPath() {
		RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
		return invoker.randomUriPath();
	}
	
}
