package exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class User {
    int id;
    String firstName;
    String lastName;
    int age;
}
