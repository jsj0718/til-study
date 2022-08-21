package me.jsj.thejava.eighttoeleven.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
//TYPE_PARAMETER: Generic의 타입 파라미터만 타겟
//TYPE_USE: 타입과 타입 파라미터 모두 타겟
@Target(ElementType.TYPE_USE)
//여러 개의 Annotation이 사용되는 컨테이너 애노테이션을 지정해줘야 한다.
//Retention과 Target은 컨테이너가 더 넓거나 같아야 한다.
@Repeatable(ChickenContainer.class)
public @interface Chicken {
    String value() default "후라이드";
}
