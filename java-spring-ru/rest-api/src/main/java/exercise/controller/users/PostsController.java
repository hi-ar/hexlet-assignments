package exercise.controller.users;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    private List<Post> posts = Data.getPosts(); //вопрос ментору: почему если добавлять напрямую в Data.get() не добавляются?

    private List<Post> eqPosts = posts;
    private List<Post> posts2 = Data.getPosts();

    @GetMapping("/users/{userId}/posts")
    ResponseEntity<List<Post>> showAllByUser(@PathVariable int userId) {
        List<Post> thisUserPosts = posts.stream().filter(p -> p.getUserId() == userId).toList();
        return ResponseEntity.ok().body(thisUserPosts);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/posts")
    Post create(@PathVariable int userId, @RequestBody Post newPost) {
        newPost.setUserId(userId);
        posts.add(newPost);
        return newPost;
    }
}
// END
