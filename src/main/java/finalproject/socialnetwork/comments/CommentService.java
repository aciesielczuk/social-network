package finalproject.socialnetwork.comments;

import finalproject.socialnetwork.posts.Post;
import finalproject.socialnetwork.posts.PostRepository;
import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Comment addComment(String username, int postId, @RequestBody String commentBody) {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        Optional<Post> postFromDB = postRepository.findById((long) postId);
        return new Comment(userFromDB.get(), postFromDB.get(), commentBody);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }


}
