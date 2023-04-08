package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // BEGIN
    @GetMapping(path = "/{postId}/comments")
    public Iterable<Comment> getCommentsByPostId(@PathVariable Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new NoSuchElementException("~~~ No such post");
        }
        return commentRepository.findByPostId(postId);
    }

    @GetMapping(path = "/{postId}/comments/{commentId}")
    public Comment getCommentByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId) {
        if (!postRepository.existsById(postId)) {
            throw new NoSuchElementException("~~~ No such post");
        }
        Comment result = commentRepository.findByPostIdAndCommentId(postId, commentId);
        if (result == null) {
            throw new NoSuchElementException("~~~ No such comment");
        }
        return result;
    }

    @PostMapping(path="/{postId}/comments")
    public Iterable<Comment> createNewCommentForPostId (@RequestBody Comment newComment, @PathVariable Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new NoSuchElementException("~~~ No such post");
        }
        Post actual = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("~~~ Post not found"));
        newComment.setPost(actual);
        commentRepository.save(newComment);

        return commentRepository.findByPostId(postId);
    }

    @PatchMapping(path = "/{postId}/comments/{commentId}")
    public Comment editComment (@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Comment newComment) {
        Post actual = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("~~~ Post not found"));
        if (commentRepository.findByPostIdAndCommentId(postId, commentId) == null) {
            throw new ResourceNotFoundException("~~~ Res (comment) not found");
        }
        newComment.setPost(actual);
        newComment.setId(commentId);
        commentRepository.save(newComment);

        return newComment;
    }

    @DeleteMapping(path="/{postId}/comments/{commentId}")
    public Iterable<Comment> deleteComment (@PathVariable long postId, @PathVariable long commentId) {
        if (!postRepository.existsById(postId)) {
            throw new NoSuchElementException("~~~ No such post");
        }
        if (commentRepository.findByPostIdAndCommentId(postId, commentId) == null) {
            throw new ResourceNotFoundException("~~~ Res (comment) not found");
        }
        commentRepository.deleteById(commentId);
        return commentRepository.findByPostId(postId);
    }
    // END
}
