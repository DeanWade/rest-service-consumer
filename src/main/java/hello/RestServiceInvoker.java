package hello;

import java.util.Random;

import org.springframework.web.client.RestTemplate;

public class RestServiceInvoker {

	private RestTemplate restTemplate;
	
	private boolean daemon;
	
	private boolean async;
	
	private String lock;
	
	public RestServiceInvoker(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public Greeting randomUriPath() {
		Random r = new Random();
		int index = r.nextInt(10) + 1;
		String url = "http://localhost:8090/provider/random/{index}";
		Greeting greeting = restTemplate.getForObject(url, Greeting.class, index);
		return greeting;
	}

	public Greeting greeting(){
		if(this.async){
			WorkerThread worker = new WorkerThread(this);
			worker.setDaemon(daemon);
			return (Greeting) worker.sendAndWait();
		}
		return doGreet();
	}
	
	public Greeting doGreet(){
		String url = "http://localhost:8090/provider/greeting" + "?lock=" + this.lock;
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


	public String getLock() {
		return lock;
	}


	public void setLock(String lock) {
		this.lock = lock;
	}
	
	
}
