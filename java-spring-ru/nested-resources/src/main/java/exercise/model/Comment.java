package exercise.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    // BEGIN
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    // END
}
