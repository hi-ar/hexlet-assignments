package exercise;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
// BEGIN

// END


class FileKVTest {

    private static Path filepath = Paths.get("src/test/resources/file").toAbsolutePath().normalize();

    @BeforeEach
    public void beforeEach() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //json представление Мапы
        String content = mapper.writeValueAsString(new HashMap<String, String>());

        //записать в file  json представление Мапы
        Files.writeString(filepath, content, StandardOpenOption.CREATE);
    }

    // BEGIN

    @Test
    void testFileKV() {
        Map<String, String> testMap = Map.of("A", "B", "1", "2", "Mon", "Tue");

        KeyValueStorage one = new FileKV(filepath.toString(), testMap);

        one.set("Jan", "Feb");
        one.unset("1");
        System.out.println(one.get("A", "такого нет"));
        System.out.println(one.get("B", "такого нет"));
        System.out.println(one.toMap());

//        assertThat(storage.get("3", "default")).isEqualTo("default");
//        assertThat(storage.get("B", "")).isEqualTo("A");
//        assertThat(storage.get("2", "")).isEqualTo("1");
    }
    // END
}
