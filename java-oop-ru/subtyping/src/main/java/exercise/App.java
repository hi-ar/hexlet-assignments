package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/*
Создайте класс App с публичным статическим методом swapKeyValue().
Метод принимает на вход объект базы данных и меняет в нём ключи и значения местами.
swapKeyValue() — полиморфный метод, он может работать с любой реализацией базы данных,
реализующей интерфейс KeyValueStorage.
 */
// BEGIN
class App {
    public static KeyValueStorage swapKeyValue(KeyValueStorage storage) {
        Map<String, String> result = new HashMap<>();

        for (String key : storage.toMap().keySet()) {
            result.put(storage.get(key, "default"), key);
        }
        storage = new InMemoryKV(result);
        return storage;
    }
}
// END
