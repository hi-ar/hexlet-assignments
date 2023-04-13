package exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String email;

    private String password;

    // BEGIN
//    @Enumerated (value = EnumType.STRING)  //убрал, стал создаваться и проходить др тесты
    @JsonIgnore
    private UserRole role;
    // END
}
