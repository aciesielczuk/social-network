package finalproject.socialnetwork.comments;

import finalproject.socialnetwork.posts.Post;
import finalproject.socialnetwork.posts.PostRepository;
import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(String token, int postId, Comment comment) {
        Optional<User> userFromDB = userRepository.findByToken(token);
        Optional<Post> postFromDB = postRepository.findById(postId);
        return new Comment(userFromDB.get(), postFromDB.get(), comment.getCommentBody());
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }


}
