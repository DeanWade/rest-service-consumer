package hello;

import org.springframework.web.client.RestTemplate;

public class WorkerThread extends Thread{

	private final Boolean daemon;

	private final RestTemplate restTemplate;
	
	private Object result;
	
	private final Object monitor = new Object();

	public WorkerThread(Boolean daemon, RestTemplate restTemplate) {
		super();
		this.daemon = daemon;
		this.restTemplate = restTemplate;
		this.setDaemon(this.daemon);
	}

	public Object sendAndWait() {
		this.start();
		waitForResult();
		return this.result;
	}

	public void waitForResult() {
		synchronized (monitor) {
			try {
				monitor.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void notifyResult() {
		synchronized (monitor) {
			monitor.notifyAll();
		}
	}

	@Override
	public void run() {
		Greeting greeting = restTemplate.getForObject("http://localhost:8081/provider/greeting", Greeting.class);
		this.result = greeting;
		notifyResult();
	}
}
