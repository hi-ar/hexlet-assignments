package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// BEGIN
class App {
    public static void swapKeyValue(KeyValueStorage storage) {
        //KeyValueStorage result = new InMemoryKV(new HashMap<>()); //вар-1, создаю Об того же класса как storage
        Map<String, String> result = new HashMap<>(); //вар-2, создаю Map чтоб в конце new InMemoryKV(result)

        for (String key : storage.toMap().keySet()) {
            //result.set(storage.get(key, "default"), key);
            result.put(storage.get(key, "default"), key);
        }
        //storage = result;
        storage = new InMemoryKV(result);
    }
}
// END
