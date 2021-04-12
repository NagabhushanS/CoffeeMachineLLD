package main;

import composers.*;
import constants.BeverageConstants;
import constants.IngredientsConstants;
import ingredients.IngredientHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import beverages.IBeverage;


public class DemoApplication {
    public static void main(String args[]) {
        int count_n = 2;
        HashMap<String, Integer> total_items_quantity = new HashMap<String, Integer>() {
            {
                put(IngredientsConstants.HOT_WATER, 5000);
                put(IngredientsConstants.HOT_MILK, 5000);
                put(IngredientsConstants.GINGER_SYRUP, 1000);
                put(IngredientsConstants.SUGAR_SYRUP, 1000);
                put(IngredientsConstants.TEA_LEAVES_SYRUP, 1000);
            }
        };

        HashMap<String, Integer> hotTeaData = new HashMap<String, Integer>() {
            {
                put(IngredientsConstants.HOT_WATER, 200);
                put(IngredientsConstants.HOT_MILK, 100);
                put(IngredientsConstants.GINGER_SYRUP, 10);
                put(IngredientsConstants.SUGAR_SYRUP, 10);
                put(IngredientsConstants.TEA_LEAVES_SYRUP, 30);
            }
        };

        HashMap<String, Integer> hotCoffeeData = new HashMap<String, Integer>() {
            {
                put(IngredientsConstants.HOT_WATER, 100);
                put(IngredientsConstants.HOT_MILK, 400);
                put(IngredientsConstants.GINGER_SYRUP, 30);
                put(IngredientsConstants.SUGAR_SYRUP, 50);
                put(IngredientsConstants.TEA_LEAVES_SYRUP, 30);
            }
        };

        HashMap<String, Integer> blackTeaData = new HashMap<String, Integer>() {
            {
                put(IngredientsConstants.HOT_WATER, 300);
                put(IngredientsConstants.GINGER_SYRUP, 30);
                put(IngredientsConstants.SUGAR_SYRUP, 50);
                put(IngredientsConstants.TEA_LEAVES_SYRUP, 30);
            }
        };

        HashMap<String, Integer> greenTeaData = new HashMap<String, Integer>() {
            {
                put(IngredientsConstants.HOT_WATER, 100);
                put(IngredientsConstants.GINGER_SYRUP, 30);
                put(IngredientsConstants.SUGAR_SYRUP, 50);
                put(IngredientsConstants.GREEN_MIXTURE, 30);
            }
        };

        // Recipee Class
        IComposer hotTeaComposer = new HotTeaComposer();
        hotTeaComposer.setRulesForComposer(hotTeaData);

        IComposer hotCoffeeComposer = new HotCoffeeComposer();
        hotCoffeeComposer.setRulesForComposer(hotCoffeeData);

        IComposer blackTeaComposer = new BlackTeaComposer();
        blackTeaComposer.setRulesForComposer(blackTeaData);

        IComposer greenTeaComposer = new GreenTeaComposer();
        greenTeaComposer.setRulesForComposer(greenTeaData);

        Map<String, IComposer> composerMap = new HashMap<String, IComposer>(){
            {
                put(BeverageConstants.BEVERAGE_BLACK_TEA, blackTeaComposer);
                put(BeverageConstants.BEVERAGE_GREEN_TEA, greenTeaComposer);
                put(BeverageConstants.BEVERAGE_HOT_COFFEE, hotCoffeeComposer);
                put(BeverageConstants.BEVERAGE_HOT_TEA, hotTeaComposer);
            }
        };

        CoffeeMachine coffeeMachine = CoffeeMachine.Builder.getNewInstance()
                .setBrandName("ChaiPoint")
                .setNoOfSlots(count_n)
                .setIngredientHolder(new IngredientHolder(total_items_quantity))
                .setComposers(composerMap)
                .build();
        coffeeMachine.setSwitchOn(true);

        // Parallel Running Multiple Threads
        Runnable greenTeaRunnable  = new Runnable() {
            @Override
            public void run() {
                coffeeMachine.requestBeverage(BeverageConstants.BEVERAGE_GREEN_TEA);
            }
        };

        Runnable hotTeaRunnable  = new Runnable() {
            @Override
            public void run() {
                coffeeMachine.requestBeverage(BeverageConstants.BEVERAGE_HOT_TEA);
            }
        };

        Runnable blackTeaRunnable  = new Runnable() {
            @Override
            public void run() {
                coffeeMachine.requestBeverage(BeverageConstants.BEVERAGE_BLACK_TEA);
            }
        };

        Runnable hotCoffeeRunnable  = new Runnable() {
            @Override
            public void run() {
                coffeeMachine.requestBeverage(BeverageConstants.BEVERAGE_HOT_COFFEE);
            }
        };

        Runnable arr[] = new Runnable[]{hotTeaRunnable,blackTeaRunnable,blackTeaRunnable};

        for(int i=0;i<5;i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
            Thread t = new Thread(arr[randomNum]);
            t.setName("["+ "Thread "+ i +"]");
            t.start();
        }
        while(true) {
            // For Holding threads execution.
        }
    }
}
