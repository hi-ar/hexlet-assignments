package exercise.controller;

import exercise.model.Post;
import exercise.repository.PostRepository;
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

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Comment> showAll(){
        return commentRepository.findAll();
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Comment show(@PathVariable Long id){
        return commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment with id " + id + " not found"));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Comment create(@RequestBody Comment newComment) {
        commentRepository.save(newComment);
        return newComment;
    }
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Comment modify(@PathVariable Long id, @RequestBody Comment newComment) {
        Comment toMod = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("§§§No comment with id " + id));
        toMod.setBody(newComment.getBody());
        toMod.setPostId(newComment.getPostId());
        commentRepository.save(toMod); //почему пересохраняется а не дописывается следующим?
        return toMod;
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    void remove(@PathVariable Long id){
        Comment toMod = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("§§§No comment with id " + id));
        commentRepository.deleteById(id);
    }
}
// END
