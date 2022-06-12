package me.jsj.item2.original;

public class NutritionFactsV1 {
    private final int servingSize; // (mL, 1회 제공량) -> 필수
    private final int servings; // (회, 총 n회 제공량) -> 필수
    private final int calories; // (1회 제공량) -> 선택
    private final int fat; // (g/1회 제공량) -> 선택
    private final int sodium; // (mg/1회 제공량) -> 선택
    private final int carbohydrate; // (g/1회 제공량) -> 선택

    public NutritionFactsV1(int servingSize, int servings) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = 0;
        this.fat = 0;
        this.sodium = 0;
        this.carbohydrate = 0;
    }

    public NutritionFactsV1(int servingSize, int servings, int calories) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = 0;
        this.sodium = 0;
        this.carbohydrate = 0;
    }

    public NutritionFactsV1(int servingSize, int servings, int calories, int fat) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = 0;
        this.carbohydrate = 0;
    }

    public NutritionFactsV1(int servingSize, int servings, int calories, int fat, int sodium) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = 0;
    }

    public NutritionFactsV1(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
