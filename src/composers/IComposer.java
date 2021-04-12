package composers;

import java.util.Map;

public interface IComposer {
    public String getBeverageType();
    public Map<String, Integer> getRulesForComposer();
    public void setRulesForComposer(Map<String, Integer> ingredientsMap);
}
