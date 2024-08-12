package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Post> showAll(){
        return postRepository.findAll();
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Post show(@PathVariable Long id){
        return postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post with id " + id + " not found"));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Post create(@RequestBody Post newPost) {
        postRepository.save(newPost);
        return newPost;
    }
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Post modify(@PathVariable Long id, @RequestBody Post newPost) {
        Post toMod = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("§§§No post with id " + id));
        toMod.setBody(newPost.getBody());
        toMod.setTitle(newPost.getTitle());
        postRepository.save(toMod); //почему пересохраняется а не дописывается следующим?
        return toMod;
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    void remove(@PathVariable Long id){
        Post toMod = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("§§§No post with id " + id));
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }
}
// END
