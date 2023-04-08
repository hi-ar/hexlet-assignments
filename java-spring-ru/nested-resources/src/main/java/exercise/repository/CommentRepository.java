package exercise.repository;

import exercise.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    // BEGIN
    Iterable<Comment> findByPostId (Long postId);
    Comment findByPostIdAndCommentId (Long postId, Long commentId);

//    boolean existsByIdAndPostId(long id, long postId);
//    public Iterable<Comment> createNewCommentForPostId (Comment newComment, long postId);
    // END
}
