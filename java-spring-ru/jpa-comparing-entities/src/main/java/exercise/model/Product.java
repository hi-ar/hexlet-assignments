package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// BEGIN
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"title", "price"})
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
//    @Column(nullable = false)
    private String title;
//    @Column(nullable = false)
    private long price;
}
// END
