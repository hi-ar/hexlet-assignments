package exercise;

import java.util.Map;
import java.util.HashMap;

/*
Интерфейс этого класса вы можете посмотреть в файле main/java/exercise/KeyValueStorage.java.
Реализуйте в классе следующие интерфейсные методы:
set() — добавляет в словарь новое значение по указанному ключу (или меняет значение, если ключ уже существует).
unset() — удаляет из словаря значение по переданному ключу
get() — возвращает значение по указанному ключу. Если такого ключа в словаре нет, возвращает значение по умолчанию.
toMap() — возвращает базу данных в виде словаря Map.
 */
// BEGIN
class InMemoryKV implements KeyValueStorage {
    private Map<String, String> localData; //хранилище

    public InMemoryKV(Map<String, String> m) { //конструктор
        localData = m;
    }

    @Override
    public void set(String key, String value) throws UnsupportedOperationException {
        localData.put(key, value);
    }

    @Override
    public void unset(String key) {
        localData.remove(key);
    }

    @Override
    public String get(String key, String defaultValue) {
        return localData.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        return null;
    }
}
// END
