package exercise;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> showAll(@RequestParam(defaultValue = "10") Integer count) {
        List<Post> result = posts.stream().limit(count).toList();
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(posts.size())).body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> getPost(@PathVariable String id) {
        Optional<Post> result = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if(result.isPresent()) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post newPost) {
        posts.add(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> modify(@RequestBody Post newPost, @PathVariable String id) {
        Optional<Post> toModify = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if(toModify.isPresent()) {
            toModify.get().setId(newPost.getId());
            toModify.get().setBody(newPost.getBody());
            toModify.get().setTitle(newPost.getTitle());
            return ResponseEntity.ok().body(toModify);
        }
        return ResponseEntity.of(toModify).status(HttpStatus.NO_CONTENT).build();
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
