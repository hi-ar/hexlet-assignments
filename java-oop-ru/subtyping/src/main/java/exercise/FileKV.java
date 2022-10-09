package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
class FileKV implements KeyValueStorage {
    private Map<String, String> localdata = new HashMap<>(); //начальное значение
    private String path = ""; //файл для сохранения данных (мапы)
    public FileKV(String path, Map<String, String> dictionary) {
        this.localdata = dictionary;
        this.path = path;
        Utils.writeFile(path, Utils.serialize(dictionary));
    }

    @Override // storage.set("A", "B")
    public void set(String key, String value) {
        localdata = Utils.unserialize(Utils.readFile(path));
        localdata.put(key, value);
        Utils.writeFile(path, Utils.serialize(localdata));
    }

    @Override
    public void unset(String key) {
        localdata = Utils.unserialize(Utils.readFile(path));
        localdata.remove(key);
        Utils.writeFile(path, Utils.serialize(localdata));
    }

    @Override
    public String get(String key, String defaultValue) {
        return localdata.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        return new HashMap<>(localdata);
    }
}
// END
