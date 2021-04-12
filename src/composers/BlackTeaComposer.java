package composers;

import static constants.BeverageConstants.BEVERAGE_BLACK_TEA;

import java.util.Map;

public class BlackTeaComposer implements IComposer {
    Map<String, Integer> ingredientsRulesForBlackTea;

    @Override
    public String getBeverageType() {
        return BEVERAGE_BLACK_TEA;
    }

    @Override
    public Map<String, Integer> getRulesForComposer() {
        return ingredientsRulesForBlackTea;
    }

    @Override
    public void setRulesForComposer(Map<String, Integer> ingredientsRulesForBlackTea) {
        this.ingredientsRulesForBlackTea = ingredientsRulesForBlackTea;
    }
}
