package beverages;

import static constants.BeverageConstants.BEVERAGE_HOT_TEA;

import java.util.logging.Logger;

public class HotTea implements IBeverage {
    private Logger logger = Logger.getLogger("HotTea");

    @Override
    public String getType() {
        return BEVERAGE_HOT_TEA;
    }

    @Override
    public void drink() {
        System.out.println("I am drinking : " + BEVERAGE_HOT_TEA);
    }
}
