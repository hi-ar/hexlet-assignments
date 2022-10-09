package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;


class AppTest {
    @Test
    void testSwapKV() {
        KeyValueStorage storage = new InMemoryKV(Map.of("A", "B"));
        storage.set("1", "2"); //мапа добавить
        App.swapKeyValue(storage);
        System.out.println(storage.toMap()); // {A=B, 1=2}
//        System.out.println(App.swapKeyValue(storage).toMap()); // {B=A, 2=1}

        assertThat(storage.get("3", "default")).isEqualTo("default"); //проходит
        assertThat(storage.get("B", "")).isEqualTo("A"); //нет, тк storage не поменялся
        assertThat(storage.get("2", "")).isEqualTo("1"); //нет, тк storage не поменялся
    }

    @Test
    void testSwapKV2() {
        KeyValueStorage storage = new InMemoryKV(Map.of("A", "B", "1", "2"));
        App.swapKeyValue(storage);
        Map<String, String> expected = Map.of("B", "A", "2", "1");
        System.out.println(storage.toMap()); // {A=B, 1=2}
        //System.out.println(App.swapKeyValue(storage).toMap()); // {B=A, 2=1}
        assertThat(storage.toMap()).isEqualTo(expected); // нет, тк storage не поменялся
    }
}
