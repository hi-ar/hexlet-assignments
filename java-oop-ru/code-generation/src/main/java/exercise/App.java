package exercise;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

// BEGIN
public class App {
    public static void save(Path path, Car car) throws IOException {
        FileWriter fileWriter = new FileWriter(path.toAbsolutePath().toFile());
        System.out.println("Otladka:" + path);
        fileWriter.write(Car.serialize(car));
        fileWriter.close();
    }
    public static Car extract(Path path) throws IOException {
        return Car.unserialize(Files.readString(path));
    }
}
// END
