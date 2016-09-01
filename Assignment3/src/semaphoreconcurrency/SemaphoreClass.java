package semaphoreconcurrency;
import java.util.Random;


public class SemaphoreClass {

	private int counter;
	
	private int signalCount;
	
	public SemaphoreClass(int counter) {
		
		if (counter <= 0) {
			throw new IllegalArgumentException("counter should be equal to one or above");
		}
		
		this.counter = counter;
		
	}
	
	public SemaphoreClass() {
		counter = 1;
	}
	
	public synchronized int getSignalCount() {
		return signalCount;
	}
	
	public synchronized void acquire() {
		
		while(signalCount >= counter) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		signalCount++;
		
		notifyAll();
		
	}
	
	public synchronized void release() {
		
		while(signalCount <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		signalCount--;
		
		notifyAll();
		
	}

	public int randomNumber(){
		Random rand = new Random();
		return rand.nextInt(2); // This would generate randomly 0 or 1
	}
}
