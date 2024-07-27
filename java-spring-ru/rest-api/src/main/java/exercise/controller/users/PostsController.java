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
@RequestMapping("/api/users")
public class PostsController {

    @GetMapping("/{userId}/posts")
    ResponseEntity<List<Post>> showAllByUser(@PathVariable int userId) {
        System.out.println("requested posts of id: " + userId);
        List<Post> thisUserPosts = Data.getPosts().stream().filter(p -> p.getUserId() == userId).toList();
        System.out.println(thisUserPosts.toString());
        return ResponseEntity.ok().body(thisUserPosts);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/posts")
    Post create(@PathVariable int userId, @RequestBody Post newPost){
        System.out.println("List first cont nums of entry: )" + Data.getPosts().size());
        Post toAdd = new Post();
        toAdd.setUserId(userId);
        toAdd.setSlug(newPost.getSlug());
        toAdd.setTitle(newPost.getTitle());
        toAdd.setBody(newPost.getBody());

        newPost.setUserId(userId); //remove
        System.out.println("§§§§§§§§§ newPost:" + newPost.getUserId() + " / " + newPost.getSlug() + " / " + newPost.getTitle() + " / " + newPost.getBody());
        System.out.println("§§§§§§§§§§§ toAdd:" + toAdd.getUserId() + " / " + toAdd.getSlug() + " / " + toAdd.getTitle() + " / " + toAdd.getBody());

        Data.getPosts().add(toAdd);
        System.out.println(Data.getPosts().add(toAdd));

        System.out.println("List now cont nums of entry: )" + Data.getPosts().size());
        return toAdd;
    }
}
// END
