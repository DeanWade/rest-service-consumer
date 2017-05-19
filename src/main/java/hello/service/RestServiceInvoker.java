package hello.service;

import java.util.Random;

import hello.model.Greeting;
import hello.remote.ProviderFeignClient;

public class RestServiceInvoker {

	private ProviderFeignClient providerFeignClient;
	
	private boolean daemon;
	
	private boolean async;
	
	private String lock;
	
	private String name;
	
	private int max;
	
	public RestServiceInvoker(ProviderFeignClient providerFeignClient) {
		this.providerFeignClient = providerFeignClient;
	}
	
	public Greeting randomUriPath() {
		Random r = new Random();
		int index = r.nextInt(max) + 1;
		return providerFeignClient.randomUriPath(index);
//		String url = "http://localhost:8090/provider/random/{index}";
//		Greeting greeting = restTemplate.getForObject(url, Greeting.class, index);
//		return greeting;
	}

	public Greeting greeting(){
		if(this.async){
			WorkerThread worker = new WorkerThread(this);
			worker.setDaemon(daemon);
			return (Greeting) worker.sendAndWait();
		}else{
			return doGreeting();
		}
	}
	
	public Greeting doGreeting(){
		return providerFeignClient.greeting(name, lock);
//		String url = "http://localhost:8090/provider/greeting" + "?lock=" + this.lock;
//		Greeting greeting = restTemplate.getForObject(url, Greeting.class);
//		return greeting;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	
}
