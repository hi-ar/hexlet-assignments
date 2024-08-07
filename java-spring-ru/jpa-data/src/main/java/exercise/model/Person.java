package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.*;

// BEGIN
@Entity
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

//    @Column(nullable = false, unique = false, length = 255)
    private String firstName;
//    @Column(nullable = false, unique = false, length = 255)
    private String lastName;
}
// END
