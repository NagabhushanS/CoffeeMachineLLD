package composers;

import static constants.BeverageConstants.BEVERAGE_HOT_COFFEE;

import java.util.Map;

public class HotCoffeeComposer implements IComposer {
    Map<String, Integer> ingredientsRulesForHotCoffee;

    @Override
    public String getBeverageType() {
        return BEVERAGE_HOT_COFFEE;
    }

    @Override
    public Map<String, Integer> getRulesForComposer() {
        return ingredientsRulesForHotCoffee;
    }

    @Override
    public void setRulesForComposer(Map<String, Integer> ingredientsRulesForHotCoffee) {
        this.ingredientsRulesForHotCoffee = ingredientsRulesForHotCoffee;
    }
}
