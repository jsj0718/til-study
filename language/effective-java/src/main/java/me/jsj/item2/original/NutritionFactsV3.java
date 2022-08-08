package me.jsj.item2.original;

public class NutritionFactsV3 {
    private int servingSize; // (mL, 1회 제공량) -> 필수
    private int servings; // (회, 총 n회 제공량) -> 필수
    private int calories; // (1회 제공량) -> 선택
    private int fat; // (g/1회 제공량) -> 선택
    private int sodium; // (mg/1회 제공량) -> 선택
    private int carbohydrate; // (g/1회 제공량) -> 선택

    public NutritionFactsV3() {
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
