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
    public static InMemoryKV swapKeyValue(KeyValueStorage incoming) {
        Map<String, String> outgoing = new HashMap<String, String>();
        for (String key : incoming.toMap().keySet()) {
            outgoing.put(incoming.get(key, "default"), key);
        }
//        for (Map.Entry e : incoming.toMap()) {
//        }
        return new InMemoryKV(outgoing);
    }
}
// END
