package main;
import java.util.concurrent.*;

public class CoffeeCustomThreadExecutor {
    private ExecutorService executor;

    public CoffeeCustomThreadExecutor(int noOfSlots) {
        this.executor = Executors.newFixedThreadPool(noOfSlots);
    }

    public boolean submitTask(int requestId, Semaphore semaphore) {
        Future obj = executor.submit(new CoffeeMakerThread(requestId));
        try {
            obj.get();
            System.out.println("\nLock released successfully by thread : "
                    + Thread.currentThread().getName());
            semaphore.release();
            return true;
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        return false;
    }
}
