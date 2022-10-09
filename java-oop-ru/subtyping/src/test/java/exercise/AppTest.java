package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;


class AppTest {
    @Test
    void testSwapKV() {
        KeyValueStorage storage = new InMemoryKV(Map.of("key", "value")); //ИФ = мапе
        storage.set("key2", "value2"); //мапа добавить
        App.swapKeyValue(storage);
        System.out.println(storage.toMap()); //
        //System.out.println(App.swapKeyValue(storage).toMap()); //

        assertThat(storage.get("key3", "default")).isEqualTo("default");
//        assertThat(storage.get("value", "")).isEqualTo("key");
//        assertThat(storage.get("value2", "")).isEqualTo("key2");
    }

    @Test
    void testSwapKV2() {
        KeyValueStorage storage = new InMemoryKV(Map.of("foo", "bar", "bar", "zoo"));
        App.swapKeyValue(storage);
        Map<String, String> expected = Map.of("bar", "foo", "zoo", "bar");
        System.out.println(storage.toMap()); //
        assertThat(storage.toMap()).isEqualTo(expected);
    }
}
