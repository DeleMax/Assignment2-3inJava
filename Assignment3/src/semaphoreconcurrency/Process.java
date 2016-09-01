package semaphoreconcurrency;

public class Process extends Thread {

	public SemaphoreClass semaphore;
	private int threadId;
	
	public Process(SemaphoreClass semaphore) {
	   this.semaphore = semaphore;
	}

	public void setThreadId(int threadId) {
	    this.threadId = threadId;
	}
	
	private void busyCode() {
	   int sleepPeriod = ((int) Math.round(500 * Math.random()));
	    try {
	       sleep(sleepPeriod);
	    } catch (InterruptedException e) {
	    }
	}

	private void noncriticalCode(){
	   busyCode();
	}

	private void criticalCode(){
	   busyCode();
	}
	
	public static void main(String[] args) {
		final int numberOfProcesses = 7;

	   SemaphoreClass semaphore = new SemaphoreClass(1);
	   Process p[] = new Process[numberOfProcesses];

	   for (int i = 0; i < numberOfProcesses; i++)
	   {
	     p[i] = new Process(semaphore);
	     p[i].setThreadId(p[i].hashCode());
	     p[i].start();
	   }

	}
	
	public void run(){
		int randomNumber = semaphore.randomNumber();
		if(randomNumber == 1){
		   for (int i = 0; i < 2; i++) {
			   System.out.print("Thread "+this.getId()+ " is now ready to write the shared resource.\n");
	           semaphore.acquire();
			   criticalCode();
			   semaphore.release();
			   System.out.print("Thread "+this.getId()+ " has finished writing the shared resource.\n");
		   }
		}else{
		   for (int i = 0; i < 2; i++) {
			   System.out.print("Thread "+this.getId()+ " is now ready to read the shared resource.\n");
		       noncriticalCode();
		       System.out.print("Thread "+this.getId()+ " has finished reading the shared resource.\n");
		   }
		}
	}

}
