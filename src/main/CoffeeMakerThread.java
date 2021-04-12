package main;
import java.util.Date;
import java.util.logging.Logger;

public class CoffeeMakerThread implements Runnable {

    private static final int COFFEE_MAKING_TIME = 12000; // in milliseconds
    private Logger logger = Logger.getLogger("CoffeeMakerThread");
    private int requestId;

    public CoffeeMakerThread(int requestId) {
        this.requestId = requestId;
    }

    public void run() {
        try {
            System.out.println("\nStarted Making Beverage at : " + new Date().getTime()
                    + "for request id : " + requestId);
            Thread.sleep(COFFEE_MAKING_TIME);
            System.out.println("\nFinished Making Beverage at : " + new Date().getTime()
                    + "for request id : " + requestId);
        } catch(Exception e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
