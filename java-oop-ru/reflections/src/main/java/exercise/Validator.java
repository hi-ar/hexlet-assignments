package exercise;

import java.lang.reflect.Field;
import java.util.*;

// BEGIN
public class Validator {
    public static List<String> validate(Address address) {
        List<String> wrongFilled = new ArrayList<>();

        for (Field myField : address.getClass().getDeclaredFields()) {
            NotNull annotation = myField.getAnnotation(NotNull.class);

            if (annotation != null) {
                try {
                    myField.setAccessible(true); //для доступа к private
                    //сравнить значение myField ОК address с null
                    if (myField.get(address) == null) {
                        wrongFilled.add(myField.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return wrongFilled;
    }

    public static Map<String, List<String>> advancedValidate(Address address) {
        Map<String, List<String>> result = new TreeMap<>();

        for (Field currentField : address.getClass().getDeclaredFields()) {
            MinLength minLengthAnn = currentField.getAnnotation(MinLength.class);

            if (minLengthAnn != null) {
                try {
                    currentField.setAccessible(true);
                    int minCharsNum = minLengthAnn.minLength();
                    if (currentField.get(address) == null
                            || currentField.get(address).toString().length() < minCharsNum) {
                        // ключ — это имя поля, не прошедшего валидацию,
                        // значение — список List строк, содержащих сообщение об ошибке.
                        // country=[length less than 4], street=[can not be null]
                        result.put(currentField.getName(), List.of("length less than " + minCharsNum));
                    }
                } catch (IllegalAccessException e) {
                    e.getStackTrace();
                }
            }
        }
        List<String> fieldsWithNull = validate(address);
        for (String s : fieldsWithNull) {
            if (result.containsKey(s)) {
                String oldValue = result.get(s).get(0);
                result.put(s, List.of(oldValue, "can not be null"));
            } else {
                result.put(s, List.of("can not be null"));
            }
        }
        return result;
    }
}
// END
