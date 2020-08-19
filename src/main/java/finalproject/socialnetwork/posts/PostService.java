package finalproject.socialnetwork.posts;

import finalproject.socialnetwork.users.User;
import finalproject.socialnetwork.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post addPost(String username, @RequestBody String postBody) {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        return new Post(userFromDB.get(), postBody);
    }
}
