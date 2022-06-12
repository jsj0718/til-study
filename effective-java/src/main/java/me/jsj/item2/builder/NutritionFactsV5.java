package me.jsj.item2.builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "Builder")
public class NutritionFactsV5 {
    private final int servingSize; // (mL, 1회 제공량) -> 필수
    private final int servings; // (회, 총 n회 제공량) -> 필수
    private final int calories; // (1회 제공량) -> 선택
    private final int fat; // (g/1회 제공량) -> 선택
    private final int sodium; // (mg/1회 제공량) -> 선택
    private final int carbohydrate; // (g/1회 제공량) -> 선택

    public static void main(String[] args) {
        NutritionFactsV5 nutritionFacts = new Builder()
                .servingSize(240)
                .servings(8)
                .calories(100)
                .sodium(35)
                .carbohydrate(27)
                .build();

        log.info(nutritionFacts.toString());
    }
}


