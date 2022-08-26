package me.jsj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //Class, Interface, Enum
@Retention(RetentionPolicy.SOURCE)
public @interface Magic {
}
