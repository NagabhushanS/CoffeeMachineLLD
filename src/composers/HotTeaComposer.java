package composers;

import static constants.BeverageConstants.BEVERAGE_HOT_TEA;

import java.util.Map;

public class HotTeaComposer implements IComposer {
    Map<String, Integer> ingredientsRulesForHotTea;

    @Override
    public String getBeverageType() {
        return BEVERAGE_HOT_TEA;
    }

    @Override
    public Map<String, Integer> getRulesForComposer() {
        return ingredientsRulesForHotTea;
    }

    @Override
    public void setRulesForComposer(Map<String, Integer> ingredientsRulesForHotTea) {
        this.ingredientsRulesForHotTea = ingredientsRulesForHotTea;
    }
}
