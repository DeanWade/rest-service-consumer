package hello;

import org.springframework.web.client.RestTemplate;

public class RestServiceInvoker {

	private RestTemplate restTemplate;
	
	private boolean daemon;
	
	private boolean async;
	
	public RestServiceInvoker(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	

	public Greeting greet(){
		if(this.async){
			WorkerThread worker = new WorkerThread(this);
			return (Greeting) worker.sendAndWait();
		}
		return doGreet();
	}
	
	public Greeting doGreet(){
		String url = "http://localhost:8090/provider/greeting";
		Greeting greeting = restTemplate.getForObject(url, Greeting.class);
		return greeting;
	}


	public boolean isDaemon() {
		return daemon;
	}


	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}


	public boolean isAsync() {
		return async;
	}


	public void setAsync(boolean async) {
		this.async = async;
	}
	
	
}
