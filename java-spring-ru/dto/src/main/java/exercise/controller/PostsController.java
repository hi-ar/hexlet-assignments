package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    private CommentDTO makeCommentDTO(Comment c) {
        return new CommentDTO(c.getId(), c.getBody());
    }

    private PostDTO makePostDTO(Post post) {
        List<CommentDTO> commentDTOList = commentRepository.findByPostId(post.getId())
                .stream().map(c -> makeCommentDTO(c)).toList();
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setComments(commentDTOList);
        return dto;
    }

    @GetMapping
    public List<PostDTO> showAll() {
        return postRepository.findAll().stream().map(this::makePostDTO).toList();
    }

    @GetMapping(path = "/{id}")
    public PostDTO show(@PathVariable long id) {
        return postRepository.findById(id).map(this::makePostDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id "+ id +" not found"));
    }
}
// END
