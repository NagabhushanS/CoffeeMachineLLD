package beverages;

import static constants.BeverageConstants.BEVERAGE_GREEN_TEA;

import java.util.logging.Logger;

public class GreenTea implements IBeverage {

    private Logger logger = Logger.getLogger("GreenTea");

    @Override
    public String getType() {
        return BEVERAGE_GREEN_TEA;
    }

    @Override
    public void drink() {
        System.out.println("I am drinking : " + BEVERAGE_GREEN_TEA);
    }

}
