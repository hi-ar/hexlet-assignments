package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    List<Post> index(@RequestParam(defaultValue = "10") Integer limit){
        return posts.stream().limit(limit).toList();
    }
    @GetMapping("/posts/{id}")
    Optional<Post> getPost(@PathVariable String id) {
        var post =  posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        return post;
    }

    @PostMapping("/posts")
    Post create(@RequestBody Post newPost) {
        posts.add(newPost);
        return newPost;
    }

    @PutMapping("/posts/{id}")
    Optional<Post> modify(@PathVariable String id, @RequestBody Post newPost) {
        var maybePost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if(maybePost.isPresent()) {
            maybePost.get().setId(newPost.getId());
            maybePost.get().setBody(newPost.getBody());
            maybePost.get().setTitle(newPost.getTitle());
        }
        return maybePost;
    }

    @DeleteMapping("/posts/{id}")
    void delete(@PathVariable String id) {
        var post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if(post.isPresent()){
            posts.remove(post);
        }
    }
    // END
}
