package hello.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import hello.model.Greeting;

@FeignClient(name = "rest-service-provider")
public interface ProviderFeignClient {

	@GetMapping("/provider/greeting")
    public Greeting greeting(
    		@RequestParam(value="name", defaultValue="World") String name,
    		@RequestParam(value="lock", defaultValue="none") String lock);
	
    @GetMapping("/provider/exception")
    public void exception();
    
	@GetMapping("/provider/random/{index}")
	public Greeting randomUriPath(@PathVariable("index") int index);
}
