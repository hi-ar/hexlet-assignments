package exercise.dto;

import jakarta.validation.constraints.Positive;
import org.openapitools.jackson.nullable.JsonNullable;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Setter
@Getter
public class CarUpdateDTO {
    private JsonNullable<String> model;
    private JsonNullable<String> manufacturer;
//    @Positive
    private JsonNullable<Long> enginePower;
}
// END
