package me.jsj.item2.builder;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class NutritionFactsV4 {
    private final int servingSize; // (mL, 1회 제공량) -> 필수
    private final int servings; // (회, 총 n회 제공량) -> 필수
    private final int calories; // (1회 제공량) -> 선택
    private final int fat; // (g/1회 제공량) -> 선택
    private final int sodium; // (mg/1회 제공량) -> 선택
    private final int carbohydrate; // (g/1회 제공량) -> 선택

/*
    public static void main(String[] args) {
        NutritionFactsV4 nutritionFacts = new Builder(240, 8)
                .calories(100)
                .sodium(35)
                .carbohydrate(27)
                .build();

        log.info(nutritionFacts.toString());
    }
*/

    public static class Builder {
        private final int servingSize;
        private final int servings;
        private int calories;
        private int fat;
        private int sodium;
        private int carbohydrate;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFactsV4 build() {
            return new NutritionFactsV4(this);
        }
    }

    public NutritionFactsV4(Builder builder) {
        this.servingSize = builder.servingSize;
        this.servings = builder.servings;
        this.calories = builder.calories;
        this.fat = builder.fat;
        this.sodium = builder.sodium;
        this.carbohydrate = builder.carbohydrate;
    }
}
