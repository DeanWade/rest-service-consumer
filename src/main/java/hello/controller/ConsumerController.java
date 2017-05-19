package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.model.Greeting;
import hello.remote.ProviderFeignClient;
import hello.service.RestServiceInvoker;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

	@Autowired
	private ProviderFeignClient providerFeignClient;

	@GetMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name,
			@RequestParam(value = "async", defaultValue = "false") boolean async,
			@RequestParam(value = "daemon", defaultValue = "false") boolean daemon,
			@RequestParam(value = "lock", defaultValue = "lock") String lock){
		RestServiceInvoker invoker = new RestServiceInvoker(providerFeignClient);
		invoker.setAsync(async);
		invoker.setDaemon(daemon);
		invoker.setLock(lock);
		invoker.setName(name);
		return invoker.greeting();
	}
	
	@GetMapping("/random")
	public Greeting randomUriPath(@RequestParam(value = "max", defaultValue = "10") int max) {
		RestServiceInvoker invoker = new RestServiceInvoker(providerFeignClient);
		invoker.setMax(max);
		return invoker.randomUriPath();
	}
	
    @GetMapping("/exception")
    public void exception() {
    	providerFeignClient.exception();
    }
    
    @GetMapping("/selfservice")
    public Greeting selfservice() {
    	return new Greeting(0L, "selfservice");
    }
	
	@ExceptionHandler(Exception.class)
	public Greeting onException(Exception e){
		Greeting greeting = new Greeting(Long.MAX_VALUE, e.getMessage());
		return greeting;
	}
}
