package ingredients;

import java.util.Map;

public class IngredientHolder {
    private Map<String, Integer> ingredientStack;

    public IngredientHolder(Map<String, Integer> ingredientStack) {
        this.ingredientStack = ingredientStack;
    }

    public Boolean isIngredientSupported(String ingredientType) {
        return ingredientStack.containsKey(ingredientType);
    }

    public int getIngredientQuantity(String ingredientType) {
        return ingredientStack.get(ingredientType);
    }

    public void setIngredientQuantity(String ingredientType, int quantity) {
        ingredientStack.put(ingredientType, quantity);
    }
}
