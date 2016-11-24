package hello;

import org.springframework.web.client.RestTemplate;

public class RestServiceInvoker {

	private RestTemplate restTemplate;
	
	private boolean direct;
	
	private boolean daemon;
	
	private boolean async;
	
	public RestServiceInvoker(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}
	

	public Greeting greet(){
		if(this.direct){
			return doGreet();
		}
		if(this.async){
			WorkerThread worker = new WorkerThread(this.daemon, this.restTemplate);
			return (Greeting) worker.sendAndWait();
		}
		return doGreet();
	}
	
	private Greeting doGreet(){
		String url = "http://localhost:8081/provider/greeting";
		Greeting greeting = restTemplate.getForObject(url, Greeting.class);
		return greeting;
	}


	public boolean isDirect() {
		return direct;
	}


	public void setDirect(boolean direct) {
		this.direct = direct;
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
