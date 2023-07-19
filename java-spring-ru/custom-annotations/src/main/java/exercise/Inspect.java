package exercise;

import org.springframework.stereotype.Component;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

// BEGIN
//при вызове методов отмеченного @ класса в консоль будет логгироваться имя метода и аргументы, с которыми он был вызван.
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inspect {
    String level() default "debug";
}
// END
