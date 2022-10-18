package exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


@Getter
@Setter
@AllArgsConstructor
class Car {
    int id;
    String brand;
    String model;
    String color;
    User owner;

    // BEGIN
    public static String serialize(Car car) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(car);
    }
    public static Car unserialize(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(str, Car.class);
    }
    // END
}
