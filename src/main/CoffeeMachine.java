package main;

import composers.IComposer;
import ingredients.IngredientHolder;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import beverages.BeverageFactory;
import beverages.IBeverage;

public class CoffeeMachine {
    private MachineInfo machineInfo;
    private Map<String, IComposer> composers;
    private IngredientHolder ingredientHolder;
    private Boolean isSwitchOn;
    private Logger logger = Logger.getLogger("CoffeeMachine");
    private CoffeeCustomThreadExecutor coffeeCustomThreadExecutor;
    private Semaphore semaphore;

    public CoffeeMachine(Builder builder) {
        machineInfo = new MachineInfo();
        machineInfo.setBrandName(builder.brandName);
        machineInfo.setNoOfSlots(builder.noOfSlots);
        this.composers = builder.composers;
        this.ingredientHolder = builder.ingredientHolder;
        this.isSwitchOn = builder.isSwitchOn;
        coffeeCustomThreadExecutor = new CoffeeCustomThreadExecutor(builder.noOfSlots);
        this.semaphore = new Semaphore(builder.noOfSlots);
    }

    public static class Builder {
        private String brandName;
        private int noOfSlots;
        private Map<String, IComposer> composers;
        private IngredientHolder ingredientHolder;
        private Boolean isSwitchOn;

        private Builder() { }

        public static Builder getNewInstance() {
            return new Builder();
        }

        public Builder setBrandName(String brandName) {
            this.brandName = brandName;
            return this;
        }

        public Builder setNoOfSlots(int noOfSlots) {
            this.noOfSlots = noOfSlots;
            return this;
        }

        public Builder setComposers(Map<String, IComposer> composers) {
            this.composers = composers;
            return this;
        }

        public Builder setIngredientHolder(IngredientHolder ingredientHolder) {
            this.ingredientHolder = ingredientHolder;
            return this;
        }

        public Builder setSwitchOn(Boolean switchOn) {
            isSwitchOn = switchOn;
            return this;
        }

        public CoffeeMachine build() {
            return new CoffeeMachine(this);
        }
    }


    public IBeverage requestBeverage(String beverageType) {
        if (!getSwitchOn()) {
            System.out.println("\nMachine Not On");
            return null;
        }
        if (!composers.containsKey(beverageType)) {
            System.out.println("\nMachine Does not support particular beverage: " + beverageType);
            return null;
        }

        System.out.println("\nThread name: " + Thread.currentThread().getName());
        boolean isAvailableSlot = semaphore.tryAcquire();
        if(!isAvailableSlot) {
            System.out.println("\nNo available slots found for thread : " + Thread.currentThread().getName());
            return null;
        } else {
            System.out.println("\n Lock acquired by thread" + Thread.currentThread().getName());
        }

        IBeverage beverage = null;
        synchronized (ingredientHolder) {
            IComposer composer = composers.get(beverageType);
            Map<String, Integer> composition = composer.getRulesForComposer();
            for (Map.Entry<String, Integer> ingredientQuantity : composition.entrySet()) {
                if (!ingredientHolder.isIngredientSupported(ingredientQuantity.getKey())) {
                    System.out.println("\nMachine Does not support ingredients for particular beverage: " + beverageType);
                    semaphore.release();
                    return null;
                }
                if (ingredientHolder.getIngredientQuantity(ingredientQuantity.getKey())
                        < ingredientQuantity.getValue()) {
                    System.out.println("\nMachine Does not have sufficient ingredients for particular beverage: " + beverageType);
                    semaphore.release();
                    return null;
                }
            }
            beverage = BeverageFactory.getBeverage(beverageType);
            makeBeverage(beverage);
        }
        Random rand = new Random();
        int requestId = Math.abs(rand.nextInt());
        boolean beverageProcessed = coffeeCustomThreadExecutor.submitTask(requestId, semaphore);
        System.out.println("Process Execution finished for task : " + Thread.currentThread().getName());
        return beverage;
    }

    private synchronized void makeBeverage(IBeverage beverage) {
        IComposer composer = composers.get(beverage.getType());
        Map<String, Integer> composition = composer.getRulesForComposer();

        for (Map.Entry<String, Integer> ingredientQuantity : composition.entrySet()) {
            int amount = ingredientHolder.getIngredientQuantity(ingredientQuantity.getKey());
            amount -= ingredientQuantity.getValue();
            ingredientHolder.setIngredientQuantity(ingredientQuantity.getKey(), amount);
        }
    }

    public MachineInfo getMachineInfo() {
        return machineInfo;
    }

    public void setMachineInfo(MachineInfo machineInfo) {
        this.machineInfo = machineInfo;
    }

    public Map<String, IComposer> getComposers() {
        return composers;
    }

    public void setComposers(Map<String, IComposer> composers) {
        this.composers = composers;
    }

    public IngredientHolder getIngredientHolder() {
        return ingredientHolder;
    }

    public void setIngredientHolder(IngredientHolder ingredientHolder) {
        this.ingredientHolder = ingredientHolder;
    }

    public Boolean getSwitchOn() {
        return isSwitchOn;
    }

    public void setSwitchOn(Boolean switchOn) {
        isSwitchOn = switchOn;
    }
}
